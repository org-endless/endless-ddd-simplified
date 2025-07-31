#!/bin/bash

set -e

# 定义变量
SOURCE_JAR="../../endless-ddd-simplified-generator/endless-ddd-simplified-generator-core/target/endless-ddd-simplified-generator.jar"
SOURCE_CONF="../../endless-ddd-simplified-generator/endless-ddd-simplified-generator-core/config"
DEST_LIB="lib"
DEST_CONF="config"
OUTPUT_ARCHIVE="endless-ddd-simplified-generator.tar.gz"
TARGET_DIR="../../cd/dockerfile/generator/resource"
CONFIG_FILE="./config/application.yaml"
TEMP_FILE="./config/application_temp.yaml"
OLD_STRING="active: dev"
NEW_STRING="active: prod"

# 创建目标文件夹
mkdir -p "$DEST_LIB" "$DEST_CONF"

# 清空目标文件夹
echo "正在清空目标文件夹 $DEST_LIB 和 $DEST_CONF ..."
rm -rf "$DEST_LIB"/*
rm -rf "$DEST_CONF"/*
rm -rf ${TARGET_DIR}/${OUTPUT_ARCHIVE}

# 拷贝文件
echo "正在拷贝 gycdp-server.jar 到 $DEST_LIB ..."
if [[ -f "$SOURCE_JAR" ]]; then
    cp -f "$SOURCE_JAR" "$DEST_LIB"
    echo "拷贝完成：$SOURCE_JAR"
else
    echo "错误：找不到文件 $SOURCE_JAR"
    exit 1
fi

echo "正在拷贝配置文件到 $DEST_CONF ..."
if [[ -d "$SOURCE_CONF" ]]; then
    cp -r "$SOURCE_CONF"/* "$DEST_CONF"
    echo "配置文件拷贝完成"
else
    echo "错误：找不到配置目录 $SOURCE_CONF"
    exit 1
fi

# 修改配置文件
if [[ -f "$CONFIG_FILE" ]]; then
    echo "正在修改 $CONFIG_FILE 中的 $OLD_STRING 为 $NEW_STRING..."
    sed "s/$OLD_STRING/$NEW_STRING/g" "$CONFIG_FILE" > "$TEMP_FILE"
    mv "$TEMP_FILE" "$CONFIG_FILE"
    echo "修改完成！"
else
    echo "错误：找不到配置文件 $CONFIG_FILE"
    exit 1
fi

# 打包并压缩
echo "正在打包文件到 $OUTPUT_ARCHIVE ..."
tar -cvf "$OUTPUT_ARCHIVE" "$DEST_LIB" "$DEST_CONF" bin

# 移动打包文件到目标目录
echo "正在移动 $OUTPUT_ARCHIVE 到 $TARGET_DIR ..."
mkdir -p "$TARGET_DIR"
mv "$OUTPUT_ARCHIVE" "$TARGET_DIR"
echo "移动成功"

echo "脚本执行完成！"
