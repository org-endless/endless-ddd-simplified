@echo off
setlocal enabledelayedexpansion
set "SCRIPT_DIR=%~dp0"
for %%i in ("%SCRIPT_DIR%\..") do set "PROJECT_DIR=%%~fi"

echo ========================================
echo EndlessDDD - Environment Check Script
echo ========================================
echo.
echo Check Env [1/3] Checking Java environment...
java -version >nul 2>&1
if not "%errorlevel%"=="0" (
    echo Java not installed, auto-installing OpenJDK 21...
    call :install_java
    if not "%errorlevel%"=="0" (
        echo Java installation failed
        goto :fail
    )
) else (
    for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr /i "version"') do (
        set "JAVA_VERSION=%%i"
        set "JAVA_VERSION=!JAVA_VERSION:~1,-1!"
    )
    echo Java installed - version: !JAVA_VERSION!

    :: Check Java version >=21
    for /f "tokens=1 delims=." %%a in ("!JAVA_VERSION!") do (
        set "MAJOR_VERSION=%%a"
    )
    if !MAJOR_VERSION! lss 21 (
        echo Java version too low, need Java 21 or higher
        echo Installing OpenJDK 21...
        call :install_java
        if not "%errorlevel%"=="0" (
            echo Java installation failed
            goto :fail
        )
    ) else (
        echo Java version is OK
    )
)

echo.
echo Check Env [2/3] Checking Rust environment...
rustc --version >nul 2>&1
if not "%errorlevel%"=="0" (
    echo Rust not installed, auto-installing Rust...
    call :install_rust
    if not "%errorlevel%"=="0" (
        echo Rust installation failed
        goto :fail
    )
) else (
    for /f "tokens=2" %%i in ('rustc --version') do (
        set "RUST_VERSION=%%i"
    )
    echo Rust installed - version: !RUST_VERSION!
)

:: Step 3: Check Tauri CLI
echo.
echo Check Env [3/3] Checking Tauri CLI...
cargo tauri --version >nul 2>&1
if not "%errorlevel%"=="0" (
    echo Tauri CLI not installed, auto-installing Tauri CLI...
    call :install_tauri
    if not "%errorlevel%"=="0" (
        echo Tauri CLI installation failed
        goto :fail
    )
) else (
    for /f "tokens=2" %%i in ('cargo tauri --version') do (
        set "TAURI_VERSION=%%i"
    )
    echo Tauri CLI installed - version: !TAURI_VERSION!
)

:: Done
echo.
echo ========================================
echo Environment check completed successfully
echo ========================================
exit /b 0

:: ---------------------------
:: Java installation function
:: ---------------------------
:install_java
echo.
echo Downloading OpenJDK 21...
powershell -Command "& { iwr -Uri 'https://download.java.net/java/GA/jdk21.0.2/13d5b2a4be90462f896e6f96bcf36db2/13/GPL/openjdk-21.0.2_windows-x64_bin.zip' -OutFile '%TEMP%\openjdk21.zip' }"
if not "%errorlevel%"=="0" (
    echo Download failed, please check network connection
    exit /b 1
)

echo Extracting OpenJDK 21...
powershell -Command "& { Expand-Archive -Path '%TEMP%\openjdk21.zip' -DestinationPath 'C:\Program Files\Java' -Force }"
if not "%errorlevel%"=="0" (
    echo Extraction failed
    exit /b 1
)

echo Configuring environment variables...
set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.2"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Verifying installation...
java -version >nul 2>&1
if not "%errorlevel%"=="0" (
    echo Java installation verification failed
    exit /b 1
)

echo OpenJDK 21 installation completed
exit /b 0

:: ---------------------------
:: Rust installation function
:: ---------------------------
:install_rust
echo.
echo Downloading Rust installer...
powershell -Command "& { iwr -Uri 'https://win.rustup.rs/x86_64' -OutFile '%TEMP%\rustup-init.exe' }"
if not "%errorlevel%"=="0" (
    echo Download failed, please check network connection
    exit /b 1
)

echo Installing Rust...
%TEMP%\rustup-init.exe -y
if not "%errorlevel%"=="0" (
    echo Rust installation failed
    exit /b 1
)

echo Configuring Rust environment...
call rustup default stable
if not "%errorlevel%"=="0" (
    echo Rust configuration failed
    exit /b 1
)

echo Verifying installation...
rustc --version >nul 2>&1
if not "%errorlevel%"=="0" (
    echo Rust installation verification failed
    exit /b 1
)

echo Rust installation completed
echo Please re-run this script to verify installation
exit /b 0

:: ---------------------------
:: Tauri CLI installation function
:: ---------------------------
:install_tauri
echo.
echo Installing Tauri CLI...
cargo install tauri-cli
if not "%errorlevel%"=="0" (
    echo Tauri CLI installation failed
    exit /b 1
)

echo Verifying installation...
cargo tauri --version >nul 2>&1
if not "%errorlevel%"=="0" (
    echo Tauri CLI installation verification failed
    exit /b 1
)

echo Tauri CLI installation completed
exit /b 0

:: ---------------------------
:: Failure exit
:: ---------------------------
:fail
echo.
echo ========================================
echo Environment check failed
echo ========================================
exit /b 1
