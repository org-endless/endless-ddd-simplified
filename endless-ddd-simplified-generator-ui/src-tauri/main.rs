mod installer;

use anyhow::{Context, Result};
use installer::{InstallOptions, Installer, SystemInfo};
use std::collections::HashMap;
use std::net::TcpStream;
use std::process::{Child, Command, Stdio};
use std::time::{Duration, Instant};
use tauri::{AppHandle, Manager};
use tokio::time::sleep;


async fn wait_port_open(host: &str, port: u16, timeout_secs: u64) -> Result<()> {
    let start = Instant::now();
    let timeout = Duration::from_secs(timeout_secs);
    loop {
        if start.elapsed() > timeout {
            anyhow::bail!("等待端口 {}:{} 超时", host, port);
        }
        if TcpStream::connect((host, port)).is_ok() {
            break;
        }
        sleep(Duration::from_secs(1)).await;
    }
    Ok(())
}

fn spawn_spring_boot(resource_dir: &std::path::Path) -> Result<Child> {
    let jar_path = resource_dir.join("lib/endless-ddd-simplified-generator.jar");
    if !jar_path.exists() {
        anyhow::bail!("JAR 文件不存在: {}", jar_path.display());
    }
    let child = Command::new("java")
        .args(&[
            "-Xmx2g",
            "-Xms1g",
            "-Dfile.encoding=UTF-8",
            "-jar",
            jar_path.to_str().unwrap(),
            "--spring.config.location=file:config/application.yaml",
        ])
        .current_dir(resource_dir)
        .stdout(Stdio::inherit())
        .stderr(Stdio::inherit())
        .spawn()
        .context("启动 Spring Boot 失败")?;
    Ok(child)
}

// Tauri 命令处理函数

/// 获取系统信息
#[tauri::command]
async fn get_system_info(app_handle: AppHandle) -> Result<SystemInfo, String> {
    let installer = Installer::new(app_handle);
    installer.get_system_info().map_err(|e| e.to_string())
}

/// 选择安装路径
#[tauri::command]
async fn select_install_path(app_handle: AppHandle) -> Result<Option<String>, String> {
    let installer = Installer::new(app_handle);
    installer.select_install_path().await.map_err(|e| e.to_string())
}

/// 验证安装路径
#[tauri::command]
async fn validate_install_path(app_handle: AppHandle, path: String) -> Result<bool, String> {
    let installer = Installer::new(app_handle);
    installer.validate_install_path(&path).map_err(|e| e.to_string())
}

/// 执行安装
#[tauri::command]
async fn install_application(app_handle: AppHandle, options: InstallOptions) -> Result<(), String> {
    let installer = Installer::new(app_handle);
    installer.install(options).await.map_err(|e| e.to_string())
}

/// 卸载应用程序
#[tauri::command]
async fn uninstall_application(app_handle: AppHandle, install_path: String) -> Result<(), String> {
    let installer = Installer::new(app_handle);
    installer.uninstall(&install_path).await.map_err(|e| e.to_string())
}

/// 获取默认安装路径
#[tauri::command]
async fn get_default_install_path() -> Result<String, String> {
    #[cfg(target_os = "windows")]
    {
        Ok("C:\\Program Files\\Endless DDD Simplified Generator".to_string())
    }
    #[cfg(target_os = "macos")]
    {
        Ok("/Applications/Endless DDD Simplified Generator".to_string())
    }
    #[cfg(target_os = "linux")]
    {
        Ok("/opt/endless-ddd-simplified-generator".to_string())
    }
}

/// 系统测试命令
#[tauri::command]
async fn run_system_test() -> Result<String, String> {
    println!("=== EndlessDDD 系统测试开始 ===");
    println!("测试时间: {}", chrono::Local::now().format("%Y-%m-%d %H:%M:%S"));

    // 1. 测试Spring Boot服务
    println!("\n1. Spring Boot服务测试:");
    match TcpStream::connect("127.0.0.1:60001") {
        Ok(_) => {
            println!("  ✅ Spring Boot服务: 运行中");
            println!("  ✅ 端口: 60001");
            println!("  ✅ 状态: 可访问");
        }
        Err(e) => {
            println!("  ❌ Spring Boot服务: 未运行");
            println!("  ❌ 错误: {}", e);
        }
    }

    // 2. 系统信息
    println!("\n2. 系统信息:");
    println!("  操作系统: {}", std::env::consts::OS);
    println!("  架构: {}", std::env::consts::ARCH);
    println!("  当前目录: {:?}", std::env::current_dir().unwrap_or_default());

    // 3. 环境变量
    println!("\n3. 环境变量:");
    println!("  JAVA_HOME: {}", std::env::var("JAVA_HOME").unwrap_or_else(|_| "未设置".to_string()));
    println!("  PATH: {}", std::env::var("PATH").unwrap_or_else(|_| "未设置".to_string()));

    println!("\n=== 系统测试完成 ===");

    Ok("测试完成，请查看终端输出".to_string())
}

/// 启动安装程序
#[tauri::command]
async fn launch_installer(app_handle: AppHandle) -> Result<(), String> {
    let window = app_handle.get_webview_window("main").unwrap();
    window.eval("window.location.href = 'installer.html';").map_err(|e| e.to_string())?;
    Ok(())
}

/// 选择文件夹
#[tauri::command]
async fn select_folder(_app_handle: AppHandle) -> Result<HashMap<String, String>, String> {
    #[cfg(target_os = "windows")]
    {
        use std::process::Command;

        // 使用PowerShell选择文件夹
        let output = Command::new("powershell")
            .args(&[
                "-Command",
                "Add-Type -AssemblyName System.Windows.Forms; $folderBrowser = New-Object System.Windows.Forms.FolderBrowserDialog; $folderBrowser.Description = '选择项目根目录'; $folderBrowser.ShowNewFolderButton = $true; if ($folderBrowser.ShowDialog() -eq 'OK') { $folderBrowser.SelectedPath } else { '' }"
            ])
            .output()
            .map_err(|e| format!("执行PowerShell命令失败: {}", e))?;

        let selected_path = String::from_utf8_lossy(&output.stdout).trim().to_string();

        if selected_path.is_empty() {
            return Err("用户取消了文件夹选择".to_string());
        }

        let mut result = HashMap::new();
        result.insert("success".to_string(), "true".to_string());
        result.insert("full_path".to_string(), selected_path.clone());
        result.insert("folder_name".to_string(), std::path::Path::new(&selected_path)
            .file_name()
            .unwrap_or_default()
            .to_string_lossy()
            .to_string());

        Ok(result)
    }

    #[cfg(target_os = "macos")]
    {
        use std::process::Command;

        // 使用osascript选择文件夹
        let script = r#"
            tell application "System Events"
                activate
                set folderPath to choose folder with prompt "选择项目根目录"
                return POSIX path of folderPath
            end tell
        "#;

        let output = Command::new("osascript")
            .args(&["-e", script])
            .output()
            .map_err(|e| format!("执行AppleScript失败: {}", e))?;

        let selected_path = String::from_utf8_lossy(&output.stdout).trim().to_string();

        if selected_path.is_empty() {
            return Err("用户取消了文件夹选择".to_string());
        }

        let mut result = HashMap::new();
        result.insert("success".to_string(), "true".to_string());
        result.insert("full_path".to_string(), selected_path.clone());
        result.insert("folder_name".to_string(), std::path::Path::new(&selected_path)
            .file_name()
            .unwrap_or_default()
            .to_string_lossy()
            .to_string());

        Ok(result)
    }

    #[cfg(target_os = "linux")]
    {
        use std::process::Command;

        // 尝试使用zenity选择文件夹
        let output = Command::new("zenity")
            .args(&["--file-selection", "--directory", "--title=选择项目根目录"])
            .output();

        match output {
            Ok(output) => {
                let selected_path = String::from_utf8_lossy(&output.stdout).trim().to_string();

                if selected_path.is_empty() {
                    return Err("用户取消了文件夹选择".to_string());
                }

                let mut result = HashMap::new();
                result.insert("success".to_string(), "true".to_string());
                result.insert("full_path".to_string(), selected_path.clone());
                result.insert("folder_name".to_string(), std::path::Path::new(&selected_path)
                    .file_name()
                    .unwrap_or_default()
                    .to_string_lossy()
                    .to_string());

                Ok(result)
            }
            Err(_) => {
                // 如果zenity不可用，返回错误
                Err("系统不支持文件夹选择对话框".to_string())
            }
        }
    }

    #[cfg(not(any(target_os = "windows", target_os = "macos", target_os = "linux")))]
    {
        Err("不支持的操作系统".to_string())
    }
}

#[tokio::main]
async fn main() -> Result<()> {
    let exe_path = std::env::current_exe().context("获取可执行文件路径失败")?;
    let exe_dir = exe_path.parent().context("获取可执行文件目录失败")?;

    // 在开发模式下，可执行文件在target\debug\目录中，需要向上查找资源目录
    let resource_dir = if exe_dir.ends_with("debug") || exe_dir.ends_with("release") {
        exe_dir.parent().unwrap().parent().unwrap()
    } else {
        exe_dir
    };

    for dir in &["lib", "config", "public"] {
        let d = resource_dir.join(dir);
        if !d.exists() {
            anyhow::bail!("未找到 {} 目录: {}", dir, d.display());
        }
    }

    // 启动 Spring Boot
    let mut spring_process = spawn_spring_boot(&resource_dir)?;
    println!("🌐 Spring Boot 启动中...");

    println!("⏳ 等待 Spring Boot 监听端口 60001...");
    wait_port_open("127.0.0.1", 60001, 30).await?;

    println!("✅ Spring Boot 已启动");

    // 启动 Tauri 窗口，加载本地静态页面（public目录）
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![
            get_system_info,
            select_install_path,
            validate_install_path,
            install_application,
            uninstall_application,
            get_default_install_path,
            run_system_test,
            launch_installer,
            select_folder,
        ])
        .setup(|app| {
            // 设置应用关闭时的清理逻辑
            let _app_handle = app.handle();

            // 在Tauri 2.x中，我们需要使用不同的方式来监听关闭事件
            // 这里我们将在应用关闭时进行清理
            Ok(())
        })
        .run(tauri::generate_context!())
        .expect("运行 Tauri 应用失败");

    // 应用关闭后的清理（备用方案）
    println!("🔄 应用已关闭，清理Spring Boot进程...");
    if let Err(e) = spring_process.kill() {
        eprintln!("❌ 关闭 Spring Boot 失败: {}", e);
        // 尝试强制关闭Java进程
        #[cfg(target_os = "windows")]
        {
            let _ = std::process::Command::new("taskkill")
                .args(&["/F", "/IM", "java.exe"])
                .output();
        }
        #[cfg(not(target_os = "windows"))]
        {
            let _ = std::process::Command::new("pkill")
                .args(&["-f", "endless-ddd-simplified-generator"])
                .output();
        }
    } else {
        println!("✅ Spring Boot 进程已关闭");
    }

    Ok(())
}
