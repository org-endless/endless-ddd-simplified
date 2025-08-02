use anyhow::Result;
use serde::{Deserialize, Serialize};
use std::fs;
use std::path::{Path, PathBuf};
use std::process::Command;

#[derive(Debug, Serialize, Deserialize)]
pub struct InstallOptions {
    pub install_path: String,
    pub create_desktop_shortcut: bool,
    pub create_start_menu_shortcut: bool,
    pub auto_start: bool,
    pub check_for_updates: bool,
}

#[derive(Debug, Serialize, Deserialize)]
pub struct SystemInfo {
    pub os: String,
    pub java_version: Option<String>,
    pub java_path: Option<String>,
    pub maven_version: Option<String>,
    pub rust_version: Option<String>,
    pub available_disk_space: u64,
    pub total_memory: u64,
}

#[derive(Debug, Serialize, Deserialize)]
pub struct InstallProgress {
    pub step: String,
    pub progress: u8,
    pub message: String,
    pub status: String, // "running", "success", "error"
}

pub struct Installer {
    #[allow(dead_code)]
    app_handle: tauri::AppHandle,
}

impl Installer {
    pub fn new(app_handle: tauri::AppHandle) -> Self {
        Self { app_handle }
    }

    /// 获取系统信息
    pub fn get_system_info(&self) -> Result<SystemInfo> {
        let os = std::env::consts::OS.to_string();

        // 检查Java版本
        let java_version = Command::new("java")
            .arg("-version")
            .output()
            .ok()
            .and_then(|output| {
                String::from_utf8_lossy(&output.stderr)
                    .lines()
                    .next()
                    .map(|s| s.to_string())
            });

        // 检查Maven版本
        let maven_version = Command::new("mvn")
            .arg("-version")
            .output()
            .ok()
            .and_then(|output| {
                String::from_utf8_lossy(&output.stdout)
                    .lines()
                    .next()
                    .map(|s| s.to_string())
            });

        // 检查Rust版本
        let rust_version = Command::new("rustc")
            .arg("--version")
            .output()
            .ok()
            .and_then(|output| {
                String::from_utf8_lossy(&output.stdout)
                    .lines()
                    .next()
                    .map(|s| s.to_string())
            });

        // 获取磁盘空间
        let available_disk_space = fs::metadata("../../endless-ddd-simplified-generator/tauri")
            .map(|metadata| metadata.len())
            .unwrap_or(0);

        // 获取内存信息（简化版本）
        let total_memory = 8 * 1024 * 1024 * 1024; // 假设8GB

        Ok(SystemInfo {
            os,
            java_version,
            java_path: None,
            maven_version,
            rust_version,
            available_disk_space,
            total_memory,
        })
    }

    /// 选择安装路径
    pub async fn select_install_path(&self) -> Result<Option<String>> {
        // 简化版本：返回默认路径
        let default_path = match std::env::consts::OS {
            "windows" => "C:\\Program Files\\Endless DDD Simplified Generator",
            "macos" => "/Applications/Endless DDD Simplified Generator",
            _ => "/opt/endless-ddd-simplified-generator",
        };
        Ok(Some(default_path.to_string()))
    }

    /// 验证安装路径
    pub fn validate_install_path(&self, path: &str) -> Result<bool> {
        let install_path = Path::new(path);

        // 检查路径是否存在
        if !install_path.exists() {
            return Ok(false);
        }

        // 检查是否有写入权限
        if !install_path.metadata()?.permissions().readonly() {
            return Ok(false);
        }

        // 检查磁盘空间
        let available_space = fs::metadata(install_path)?.len();
        if available_space < 500 * 1024 * 1024 { // 500MB
            return Ok(false);
        }

        Ok(true)
    }

    /// 执行安装
    pub async fn install(&self, options: InstallOptions) -> Result<()> {
        let install_path = Path::new(&options.install_path);

        // 创建安装目录
        fs::create_dir_all(install_path)?;

        // 复制文件
        self.copy_application_files(install_path).await?;

        // 创建快捷方式
        if options.create_desktop_shortcut {
            self.create_desktop_shortcut(install_path).await?;
        }

        if options.create_start_menu_shortcut {
            self.create_start_menu_shortcut(install_path).await?;
        }

        // 设置开机自启
        if options.auto_start {
            self.setup_auto_start(install_path).await?;
        }

        Ok(())
    }

    /// 复制应用程序文件
    async fn copy_application_files(&self, install_path: &Path) -> Result<()> {
        let exe_path = std::env::current_exe()?;
        let exe_dir = exe_path.parent().unwrap();

        // 复制lib目录
        let lib_src = exe_dir.join("../lib");
        let lib_dst = install_path.join("../lib");
        if lib_src.exists() {
            self.copy_directory(&lib_src, &lib_dst).await?;
        }

        // 复制config目录
        let config_src = exe_dir.join("../config");
        let config_dst = install_path.join("../config");
        if config_src.exists() {
            self.copy_directory(&config_src, &config_dst).await?;
        }

        // 复制public目录
        let public_src = exe_dir.join("../public");
        let public_dst = install_path.join("../public");
        if public_src.exists() {
            self.copy_directory(&public_src, &public_dst).await?;
        }

        // 复制可执行文件
        let exe_dst = install_path.join("endless-ddd-simplified-generator.exe");
        fs::copy(&exe_path, &exe_dst)?;

        Ok(())
    }

    /// 复制目录
    async fn copy_directory(&self, src: &Path, dst: &Path) -> Result<()> {
        if !src.exists() {
            return Ok(());
        }

        if !dst.exists() {
            fs::create_dir_all(dst)?;
        }

        for entry in fs::read_dir(src)? {
            let entry = entry?;
            let src_path = entry.path();
            let dst_path = dst.join(entry.file_name());

            if src_path.is_dir() {
                Box::pin(self.copy_directory(&src_path, &dst_path)).await?;
            } else {
                fs::copy(&src_path, &dst_path)?;
            }
        }

        Ok(())
    }

    /// 创建桌面快捷方式
    async fn create_desktop_shortcut(&self, install_path: &Path) -> Result<()> {
        #[cfg(target_os = "windows")]
        {
            use std::process::Command;

            let desktop = std::env::var("USERPROFILE")
                .map(|p| PathBuf::from(p).join("Desktop"))
                .unwrap_or_else(|_| PathBuf::from("C:\\Users\\Public\\Desktop"));

            let shortcut_path = desktop.join("Endless DDD Simplified Generator.lnk");

            // 使用PowerShell创建快捷方式
            let script = format!(
                r#"
                $WshShell = New-Object -comObject WScript.Shell
                $Shortcut = $WshShell.CreateShortcut("{}")
                $Shortcut.TargetPath = "{}"
                $Shortcut.WorkingDirectory = "{}"
                $Shortcut.Save()
                "#,
                shortcut_path.display(),
                install_path.join("endless-ddd-simplified-generator.exe").display(),
                install_path.display()
            );

            Command::new("powershell")
                .args(&["-Command", &script])
                .output()?;
        }

        Ok(())
    }

    /// 创建开始菜单快捷方式
    async fn create_start_menu_shortcut(&self, install_path: &Path) -> Result<()> {
        #[cfg(target_os = "windows")]
        {
            use std::process::Command;

            let start_menu = std::env::var("APPDATA")
                .map(|p| PathBuf::from(p).join("Microsoft\\Windows\\Start Menu\\Programs"))
                .unwrap_or_else(|_| PathBuf::from("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs"));

            let shortcut_path = start_menu.join("Endless DDD Simplified Generator.lnk");

            // 使用PowerShell创建快捷方式
            let script = format!(
                r#"
                $WshShell = New-Object -comObject WScript.Shell
                $Shortcut = $WshShell.CreateShortcut("{}")
                $Shortcut.TargetPath = "{}"
                $Shortcut.WorkingDirectory = "{}"
                $Shortcut.Save()
                "#,
                shortcut_path.display(),
                install_path.join("endless-ddd-simplified-generator.exe").display(),
                install_path.display()
            );

            Command::new("powershell")
                .args(&["-Command", &script])
                .output()?;
        }

        Ok(())
    }

    /// 设置开机自启
    async fn setup_auto_start(&self, install_path: &Path) -> Result<()> {
        #[cfg(target_os = "windows")]
        {
            use std::process::Command;

            let exe_path = install_path.join("endless-ddd-simplified-generator.exe");

            // 使用PowerShell添加到注册表
            let script = format!(
                r#"
                $key = "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run"
                Set-ItemProperty -Path $key -Name "EndlessDDDGenerator" -Value "{}"
                "#,
                exe_path.display()
            );

            Command::new("powershell")
                .args(&["-Command", &script])
                .output()?;
        }

        Ok(())
    }

    /// 卸载应用程序
    pub async fn uninstall(&self, install_path: &str) -> Result<()> {
        let path = Path::new(install_path);

        if path.exists() {
            fs::remove_dir_all(path)?;
        }

        // 删除快捷方式
        self.remove_shortcuts().await?;

        // 删除开机自启
        self.remove_auto_start().await?;

        Ok(())
    }

    /// 删除快捷方式
    async fn remove_shortcuts(&self) -> Result<()> {
        #[cfg(target_os = "windows")]
        {
            // 删除桌面快捷方式
            let desktop = std::env::var("USERPROFILE")
                .map(|p| PathBuf::from(p).join("Desktop"))
                .unwrap_or_else(|_| PathBuf::from("C:\\Users\\Public\\Desktop"));

            let desktop_shortcut = desktop.join("Endless DDD Simplified Generator.lnk");
            if desktop_shortcut.exists() {
                fs::remove_file(desktop_shortcut)?;
            }

            // 删除开始菜单快捷方式
            let start_menu = std::env::var("APPDATA")
                .map(|p| PathBuf::from(p).join("Microsoft\\Windows\\Start Menu\\Programs"))
                .unwrap_or_else(|_| PathBuf::from("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs"));

            let start_menu_shortcut = start_menu.join("Endless DDD Simplified Generator.lnk");
            if start_menu_shortcut.exists() {
                fs::remove_file(start_menu_shortcut)?;
            }
        }

        Ok(())
    }

    /// 删除开机自启
    async fn remove_auto_start(&self) -> Result<()> {
        #[cfg(target_os = "windows")]
        {
            use std::process::Command;

            let script = r#"
                $key = "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run"
                Remove-ItemProperty -Path $key -Name "EndlessDDDGenerator" -ErrorAction SilentlyContinue
            "#;

            Command::new("powershell")
                .args(&["-Command", script])
                .output()?;
        }

        Ok(())
    }
} 
