@echo off
setlocal enabledelayedexpansion

echo ========================================
echo EndlessDDD - Production Build Script
echo ========================================

:: Clean build artifacts
echo.
echo [1/4] Cleaning build artifacts...
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
echo [2/4] Building JAR file...
call "%~dp0build-jar.bat"
if %errorlevel% neq 0 (
    echo Error: JAR build failed
    pause
    exit /b 1
)

:: Build installer
echo.
echo [3/4] Building installer...
echo Using Tauri to build production version...
cargo tauri build
if %errorlevel% neq 0 (
    echo Error: Tauri build failed
    pause
    exit /b 1
)

:: Show results
echo.
echo [4/4] Build completed
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
echo.
pause
