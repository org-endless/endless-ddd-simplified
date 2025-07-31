@echo off
setlocal enabledelayedexpansion

:: 定义变量
set "SOURCE_JAR=..\..\endless-ddd-simplified-generator\endless-ddd-simplified-generator-core\target\endless-ddd-simplified-generator.jar"
set "SOURCE_CONF=..\..\endless-ddd-simplified-generator\endless-ddd-simplified-generator-core\config"
set "DEST_LIB=lib"
set "DEST_CONF=config"
set "OUTPUT_ARCHIVE=endless-ddd-simplified-generator.tar.gz"
set "TARGET_DIR=..\..\cd\dockerfile\generator\resource"
set "CONFIG_FILE=config\application.yaml"
set "TEMP_FILE=config\application_temp.yaml"
set "OLD_STRING=active: dev"
set "NEW_STRING=active: prod"

:: 切换到脚本所在目录，确保相对路径正确
pushd "%~dp0"

:: 清空目标文件夹
if exist "%DEST_LIB%" (
    echo 清空目标目录："%DEST_LIB%"
    rmdir /S /Q "%DEST_LIB%"
)

if exist "%DEST_CONF%" (
    echo 清空目标目录："%DEST_CONF%"
    rmdir /S /Q "%DEST_CONF%"
)

if exist "%TARGET_DIR%\%OUTPUT_ARCHIVE%" (
    echo 删除旧文件："%TARGET_DIR%\%OUTPUT_ARCHIVE%"
    del /Q "%TARGET_DIR%\%OUTPUT_ARCHIVE%"
)

:: 创建目标文件夹
if not exist "%DEST_LIB%" (
    echo 创建目标目录："%DEST_LIB%"
    mkdir "%DEST_LIB%"
)

if not exist "%DEST_CONF%" (
    echo 创建目标目录："%DEST_CONF%"
    mkdir "%DEST_CONF%"
)

:: 拷贝 JAR 文件
echo 正在拷贝 gycdp-server.jar 到 "%DEST_LIB%" ...
if exist "%SOURCE_JAR%" (
    copy /Y "%SOURCE_JAR%" "%DEST_LIB%"
    echo 拷贝完成："%SOURCE_JAR%"
) else (
    echo 错误：找不到文件 "%SOURCE_JAR%"
    exit /b 1
)

:: 拷贝配置文件夹
echo 正在拷贝配置文件到 "%DEST_CONF%" ...
if exist "%SOURCE_CONF%" (
    xcopy /E /I /Y "%SOURCE_CONF%" "%DEST_CONF%"
    echo 配置文件拷贝完成
) else (
    echo 错误：找不到配置目录 "%SOURCE_CONF%"
    exit /b 1
)

:: 修改配置文件
if exist "%CONFIG_FILE%" (
    echo 正在修改 "%CONFIG_FILE%" 中的 "%OLD_STRING%" 为 "%NEW_STRING%"...
    > "%TEMP_FILE%" (
        for /f "delims=" %%i in ('type "%CONFIG_FILE%"') do (
            set "line=%%i"
            set "line=!line:%OLD_STRING%=%NEW_STRING%!"
            echo !line!
        )
    )
    move /Y "%TEMP_FILE%" "%CONFIG_FILE%"
    echo 修改完成！
) else (
    echo 错误：找不到配置文件 "%CONFIG_FILE%"
    exit /b 1
)

:: 检查是否安装 tar
where tar >nul 2>nul
if %errorlevel% neq 0 (
    echo 错误：未找到 tar 工具，请安装后重试！
    exit /b 1
)

:: 打包并压缩
echo 正在打包文件到 "%OUTPUT_ARCHIVE%" ...
tar -czvf "%OUTPUT_ARCHIVE%" "%DEST_LIB%" "%DEST_CONF%" bin
if %errorlevel% neq 0 (
    echo 打包失败！
    exit /b 1
)
echo 打包成功："%OUTPUT_ARCHIVE%"

:: 移动压缩包到目标目录
echo 正在移动 "%OUTPUT_ARCHIVE%" 到 "%TARGET_DIR%" ...
if exist "%TARGET_DIR%" (
    move /Y "%OUTPUT_ARCHIVE%" "%TARGET_DIR%"
    echo 移动成功
) else (
    echo 错误：目标目录 "%TARGET_DIR%" 不存在！
    exit /b 1
)

:: 清理临时文件
echo 脚本执行完成！
popd
