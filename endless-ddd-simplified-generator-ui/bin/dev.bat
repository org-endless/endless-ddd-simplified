@echo off
setlocal enabledelayedexpansion
set "SCRIPT_DIR=%~dp0"
for %%i in ("%SCRIPT_DIR%\..") do set "PROJECT_DIR=%%~fi"

call "%SCRIPT_DIR%build-jar.bat"
if not "%errorlevel%"=="0" (
    echo Error: JAR build failed
    pause
    exit /b 1
)
echo.
echo.
echo ========================================
echo EndlessDDD - Development Mode
echo ========================================
echo.
echo Development [1/2] Cleaning build artifacts...
echo Cleaning lib and config directories...
if exist "%PROJECT_DIR%target\release\lib" (
    rmdir /s /q "%PROJECT_DIR%target\release\lib\*"
)
if exist "%PROJECT_DIR%target\release\config" (
    rmdir /s /q "%PROJECT_DIR%target\release\config\*"
)
if exist "%PROJECT_DIR%target\release\bundle" (
    rmdir /s /q "%PROJECT_DIR%target\release\bundle\*"
)
if exist "%PROJECT_DIR%Cargo.lock" (
    rmdir /s /q "%PROJECT_DIR%Cargo.lock"
)
echo.
echo Development [2/2] Starting development application...
echo Starting EndlessDDD application for debugging...
cd /d %PROJECT_DIR% && cargo run --no-default-features
echo.
echo ========================================
echo Development mode ended
echo ========================================
