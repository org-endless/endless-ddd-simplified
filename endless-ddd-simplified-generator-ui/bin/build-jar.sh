#!/bin/bash

echo "========================================"
echo "EndlessDDD - JAR Build Script"
echo "========================================"

# Check environment
echo "[1/5] Checking build environment..."
if ! bash "$(dirname "$0")/check-env.sh"; then
    echo "Error: Environment check failed, please install missing dependencies first"
    exit 1
fi

# Clean cache
echo
echo "[2/5] Cleaning cache..."
cd "$(dirname "$0")/../../.."
echo "Cleaning Maven cache..."
./mvnw clean
if [ -d "endless-ddd-simplified-generator-ui/target" ]; then
    echo "Cleaning Rust cache..."
    cd endless-ddd-simplified-generator-ui
    cargo clean
    cd ..
fi

# Build JAR
echo
echo "[3/5] Building JAR file..."
echo "Using Maven wrapper to build Spring Boot application..."
if ! ./mvnw clean package -DskipTests --settings .mvn/settings.xml; then
    echo "Error: Maven build failed"
    exit 1
fi

# Copy JAR file and configuration
echo
echo "[4/5] Copying JAR file and configuration..."
if [ ! -f "endless-ddd-simplified-generator-core/target/endless-ddd-simplified-generator.jar" ]; then
    echo "Error: JAR file not generated"
    exit 1
fi

# Clean target directories before copying
echo "Cleaning target directories..."
if [ -d "endless-ddd-simplified-generator-ui/lib" ]; then
    echo "Cleaning lib directory..."
    rm -rf "endless-ddd-simplified-generator-ui/lib"
fi
if [ -d "endless-ddd-simplified-generator-ui/config" ]; then
    echo "Cleaning config directory..."
    rm -rf "endless-ddd-simplified-generator-ui/config"
fi

# Create directories
echo "Creating directories..."
mkdir -p "endless-ddd-simplified-generator-ui/lib"
mkdir -p "endless-ddd-simplified-generator-ui/config"

if ! cp "endless-ddd-simplified-generator-core/target/endless-ddd-simplified-generator.jar" "endless-ddd-simplified-generator-ui/lib/"; then
    echo "Error: JAR file copy failed"
    exit 1
fi

# Copy configuration files
echo "Copying configuration files..."

if ! cp "endless-ddd-simplified-generator-core/config/application.yaml" "endless-ddd-simplified-generator-ui/config/" 2>/dev/null; then
    echo "Warning: application.yaml copy failed, using default"
fi

if ! cp "endless-ddd-simplified-generator-core/config/application-dev.yaml" "endless-ddd-simplified-generator-ui/config/" 2>/dev/null; then
    echo "Warning: application-dev.yaml copy failed"
fi

if ! cp "endless-ddd-simplified-generator-core/config/application-prod.yaml" "endless-ddd-simplified-generator-ui/config/" 2>/dev/null; then
    echo "Warning: application-prod.yaml copy failed"
fi

# Modify application.yaml to use prod profile
echo
echo "[5/5] Modifying Spring Boot configuration for production..."
CONFIG_FILE="endless-ddd-simplified-generator-ui/config/application.yaml"
TEMP_FILE="/tmp/application.yaml.tmp"
OLD_STRING="active: dev"
NEW_STRING="active: prod"

if [ -f "$CONFIG_FILE" ]; then
    echo "Updating application.yaml to use prod profile..."
    # 修改配置文件
    while IFS= read -r line; do
        echo "${line//$OLD_STRING/$NEW_STRING}" >> "$TEMP_FILE"
    done < "$CONFIG_FILE"
    mv "$TEMP_FILE" "$CONFIG_FILE"
    echo "Successfully updated application.yaml to use prod profile"
else
    echo "Creating default application.yaml with prod profile..."
    cat > "$CONFIG_FILE" << 'EOF'
spring:
  profiles:
    active: prod
  application:
    name: endless-ddd-simplified-generator
  freemarker:
    enabled: true
    template-loader-path: classpath:/templates/freemarker/
    suffix: .ftl
    check-template-location: false
  mvc:
    throw-exception-if-no-handler-found: true
  actuator:
    endpoints:
      web:
        exposure:
          include: health,info
        base-path: /actuator
    endpoint:
      health:
        show-details: always
EOF
    echo "Created default application.yaml with prod profile"
fi

echo
echo "========================================"
echo "JAR build completed successfully"
echo "========================================"
echo "JAR file location: endless-ddd-simplified-generator/tauri/lib/endless-ddd-simplified-generator.jar"
echo "Config files location: endless-ddd-simplified-generator/tauri/config/"
echo "Spring Boot profile: prod"
echo
