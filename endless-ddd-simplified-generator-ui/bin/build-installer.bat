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
echo EndlessDDD - Production Build Script
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
echo [2/3] Building installer...
echo Using Tauri to build production version...
cd /d %PROJECT_DIR% && cargo tauri build
if not "%errorlevel%"=="0" (
    echo Error: Tauri build failed
    pause
    exit /b 1
)
echo.
echo [3/3] Build completed
echo ========================================
echo Production build completed successfully
echo ========================================
echo.
echo Build results:
echo - Executable: target\release\endless-ddd.exe
echo - Installer: target\release\bundle\msi\endless-ddd_1.0.0_x64_en-US.msi
echo.
echo Installer locations:
for %%f in (target\release\bundle\msi\*.msi) do (
    echo - %%f
)
for %%f in (target\release\bundle\deb\*.deb) do (
    echo - %%f
)
for %%f in (target\release\bundle\dmg\*.dmg) do (
    echo - %%f
)
