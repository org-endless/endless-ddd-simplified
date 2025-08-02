<#-- @ftlvariable name="projectAggregate" type="org.endless.ddd.simplified.generator.components.generator.project.domain.entity.ProjectAggregate" -->
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${projectAggregate.getGroupId()}</groupId>
    <artifactId>${projectAggregate.getProjectArtifactId()}</artifactId>
    <version>${projectAggregate.getVersion()}</version>
    <packaging>pom</packaging>

    <name>${projectAggregate.getName()}</name>
    <description>${projectAggregate.getDescription()}</description>
    <#-- 调试用，实际生成时可删除 -->

    <properties>
        <!-- Project -->
        <#if projectAggregate.getJavaVersion().getCode() == "JAVA8">
            <#assign javaVersion = "1.8">
            <#assign springBootVersion = "2.7.18">
            <#assign springDocVersion = "1.8.0">
        <#elseif projectAggregate.getJavaVersion().getCode() == "JAVA21">
            <#assign javaVersion = "21">
            <#assign springBootVersion = "3.5.4">
            <#assign springDocVersion = "2.8.9">
        </#if>
        <java.version>${javaVersion}</java.version>
        <encoding>UTF-8</encoding>
        <projectAggregate.build.sourceEncoding>${r'${encoding}'}</projectAggregate.build.sourceEncoding>
        <projectAggregate.reporting.outputEncoding>${r'${encoding}'}</projectAggregate.reporting.outputEncoding>
        <maven.compiler.encoding>${r'${encoding}'}</maven.compiler.encoding>
        <maven.compiler.source>${r'${java.version}'}</maven.compiler.source>
        <maven.compiler.target>>${r'${java.version}'}</maven.compiler.target>
        <maven-compiler-plugin.version>3.14.0</maven-compiler-plugin.version>

        <!-- Endless -->
        <endless-ddd.version>1.0.0-SNAPSHOT</endless-ddd.version>
        <!-- Framework -->
        <spring-boot.version>${springBootVersion}</spring-boot.version>
        <druid.version>1.2.27</druid.version>
        <#if projectAggregate.getPersistenceFramework().getCode() == "MYBATIS_PLUS">
            <mybatis-plus.version>3.5.12</mybatis-plus.version>
            <pagehelper.version>2.1.1</pagehelper.version>
        </#if>

        <!-- Infrastructure -->
        <minio.version>8.5.17</minio.version>
        <mysql.version>9.4.0</mysql.version>

        <!-- Utils -->
        <jjwt.version>0.12.6</jjwt.version>
        <bcprov.version>1.81</bcprov.version>
        <fastjson2.version>2.0.58</fastjson2.version>
        <snakeyaml.version>2.4</snakeyaml.version>

        <!-- Dev -->
        <lombok.version>1.18.38</lombok.version>
        <springdoc.version>${springDocVersion}</springdoc.version>
        <therapi-javadoc.version>0.15.0</therapi-javadoc.version>
    </properties>

    <!-- 服务声明 -->
    <modules>
        <#list projectAggregate.getServiceArtifactIds() as serviceArtifactId>
            <module>${serviceArtifactId}</module>
        </#list>
    </modules>

    <!-- 依赖声明 -->
    <dependencyManagement>
        <dependencies>
            <!-- Endless DDD -->
            <dependency>
                <groupId>org.endless</groupId>
                <artifactId>endless-ddd-simplified-starter</artifactId>
                <version>${r'${endless-ddd.version}'}</version>
            </dependency>

            <!-- Spring Boot -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${r'${spring-boot.version}'}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- Druid -->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid-spring-boot-starter</artifactId>
                <version>${r'${druid.version}'}</version>
            </dependency>
            <#if projectAggregate.getPersistenceFramework().getCode() == "MYBATIS_PLUS">
                <!-- Mybatis Plus -->
                <dependency>
                    <groupId>com.baomidou</groupId>
                    <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
                    <version>${r'${mybatis-plus.version}'}</version>
                </dependency>
                <!-- Page Helper -->
                <dependency>
                    <groupId>com.github.pagehelper</groupId>
                    <artifactId>pagehelper-spring-boot-starter</artifactId>
                    <version>${r'${pagehelper.version}'}</version>
                </dependency>
            </#if>

            <!-- Infrastructure -->
            <!-- MinIO -->
            <dependency>
                <groupId>io.minio</groupId>
                <artifactId>minio</artifactId>
                <version>${r'${minio.version}'}</version>
            </dependency>
            <!-- Mysql -->
            <dependency>
                <groupId>com.mysql</groupId>
                <artifactId>mysql-connector-j</artifactId>
                <version>${r'${mysql.version}'}</version>
            </dependency>

            <!-- Utils -->
            <!-- Jjwt -->
            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt-api</artifactId>
                <version>${r'${jjwt.version}'}</version>
            </dependency>
            <!-- Bouncy Castle -->
            <dependency>
                <groupId>org.bouncycastle</groupId>
                <artifactId>bcprov-jdk18on</artifactId>
                <version>${r'${bcprov.version}'}</version>
            </dependency>
            <!-- Fastjson2 -->
            <dependency>
                <groupId>com.alibaba.fastjson2</groupId>
                <artifactId>fastjson2</artifactId>
                <version>${r'${fastjson2.version}'}</version>
            </dependency>
            <!-- Snakeyaml -->
            <dependency>
                <groupId>org.yaml</groupId>
                <artifactId>snakeyaml</artifactId>
                <version>${r'${snakeyaml.version}'}</version>
            </dependency>

            <!-- Dev -->
            <!-- Lombok -->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${r'${lombok.version}'}</version>
            </dependency>
            <!-- Springdoc -->
            <#if projectAggregate.getJavaVersion().getCode() == "JAVA8">
                <dependency>
                    <groupId>org.springdoc</groupId>
                    <artifactId>springdoc-openapi-ui</artifactId>
                    <version>${r'${springdoc.version}'}</version>
                </dependency>
                <dependency>
                    <groupId>org.springdoc</groupId>
                    <artifactId>springdoc-openapi-webmvc-core</artifactId>
                    <version>${r'${springdoc.version}'}</version>
                </dependency>
                <dependency>
                    <groupId>org.springdoc</groupId>
                    <artifactId>springdoc-openapi-security</artifactId>
                    <version>${r'${springdoc.version}'}</version>
                </dependency>
                <dependency>
                    <groupId>org.springdoc</groupId>
                    <artifactId>springdoc-openapi-javadoc</artifactId>
                    <version>${r'${springdoc.version}'}</version>
                </dependency>
            <#elseif projectAggregate.getJavaVersion().getCode() == "JAVA21">
                <dependency>
                    <groupId>org.springdoc</groupId>
                    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
                    <version>${r'${springdoc.version}'}</version>
                </dependency>
                <dependency>
                    <groupId>com.github.therapi</groupId>
                    <artifactId>therapi-runtime-javadoc</artifactId>
                    <version>>${r'${therapi-javadoc.version}'}</version>
                </dependency>
            </#if>
        </dependencies>
    </dependencyManagement>

    <build>
        <pluginManagement>
            <plugins>
                <!-- Maven -->
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>${r'${maven-compiler-plugin.version}'}</version>
                    <configuration>
                        <annotationProcessorPaths>
                            <path>
                                <groupId>com.github.therapi</groupId>
                                <artifactId>therapi-runtime-javadoc-scribe</artifactId>
                                <version>${r'${therapi-javadoc.version}'}</version>
                            </path>
                            <path>
                                <groupId>org.projectAggregatelombok</groupId>
                                <artifactId>lombok</artifactId>
                                <version>${r'${lombok.version}'}</version>
                            </path>
                        </annotationProcessorPaths>
                        <source>${r'${java.version}'}</source>
                        <target>${r'${java.version}'}</target>
                    </configuration>
                </plugin>

                <!-- Spring Boot Maven -->
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <version>${r'${spring-boot.version}'}</version>
                    <configuration>
                        <excludes>
                            <exclude>
                                <groupId>org.springframework.boot</groupId>
                                <artifactId>spring-boot-devtools</artifactId>
                            </exclude>
                        </excludes>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>
</project>
