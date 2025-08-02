@echo off
setlocal enabledelayedexpansion

echo ========================================
echo EndlessDDD - Development Mode
echo ========================================

:: Clean build artifacts
echo.
echo [1/5] Cleaning build artifacts...
cd /d "%~dp0..\..\.."
echo Cleaning Maven build artifacts...
call mvnw.cmd clean
cd endless-ddd-simplified-generator-ui
echo Cleaning Rust build artifacts...
if exist "target" (
    rmdir /s /q target
)
if exist "Cargo.lock" (
    del Cargo.lock
)

:: Build JAR
echo.
echo [2/5] Building JAR file...
call "%~dp0build-jar.bat"
if %errorlevel% neq 0 (
    echo Error: JAR build failed
    pause
    exit /b 1
)

:: Clean Rust cache
echo.
echo [3/5] Cleaning Rust cache...
cd /d "%~dp0.."
if exist "target" (
    echo Cleaning Rust build cache...
    cargo clean
)
pause

:: Compile application
echo.
echo [4/5] Compiling Tauri application...
if not exist "lib\endless-ddd-simplified-generator.jar" (
    echo Error: JAR file not found, please run build-jar.bat first
    pause
    exit /b 1
)

echo Compiling Rust application...
cargo build --no-default-features
if %errorlevel% neq 0 (
    echo Error: Rust compilation failed
    pause
    exit /b 1
)

:: Start application
echo.
echo [5/5] Starting development application...
echo Starting EndlessDDD application for debugging...
echo.
echo Tips:
echo - Application will open desktop window automatically
echo - Click "Environment Test" to test system status
echo - Click "Project Wizard" to create new project
echo - Closing window will automatically clean Spring Boot process
echo.
pause

echo Starting application...
cargo run --no-default-features

echo.
echo ========================================
echo Development mode ended
echo ========================================
pause
