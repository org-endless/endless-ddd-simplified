#!/bin/sh
APPNAME=endless-ddd-simplified-generator
HOME=/${APPNAME}
GC_HOME=${HOME}/logs/gc

GC_OPTS="-Xlog:gc+heap*:file=${GC_HOME}/gc.log.%t"

# 配置 Java 启动选项
JAVA_OPTS="-Xms4096m -Xmx4096m ${GC_OPTS}"

# 确保 GC_HOME 目录存在
mkdir -p "${GC_HOME}"

# 确保 JAVA_HOME 环境变量已设置
if [ -z "$JAVA_HOME" ]; then
  echo "JAVA_HOME 未在环境变量中配置"
  exit 1
fi

# 确保 JAVA_HOME 指向正确的目录
if [ ! -d "$JAVA_HOME" ]; then
  echo "JAVA_HOME 指向的目录不存在: $JAVA_HOME"
  exit 1
fi

# 设置日志目录权限
chmod -R 755 ${GC_HOME}

# 导出 JAVA_OPTS 以便子进程使用
export JAVA_OPTS

# 使用 JAVA_HOME 运行 Java 应用，确保 JAVA_OPTS 被使用
java ${JAVA_OPTS} -Dfile.encoding=UTF-8  -jar ${HOME}/lib/${APPNAME}.jar --spring.config.location=file:${HOME}/config/application.yaml
