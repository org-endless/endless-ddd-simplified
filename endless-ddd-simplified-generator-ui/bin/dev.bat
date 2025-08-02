@echo off
setlocal enabledelayedexpansion

echo ========================================
echo EndlessDDD - Development Mode
echo ========================================

echo [1/3] Cleaning build artifacts...
cd /d "%~dp0..\..\.."
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
echo [2/3] Building JAR file...
call "%~dp0build-jar.bat"
if %errorlevel% neq 0 (
    echo Error: JAR build failed
    pause
    exit /b 1
)

:: Start application
echo.
echo [3/3] Starting development application...
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
