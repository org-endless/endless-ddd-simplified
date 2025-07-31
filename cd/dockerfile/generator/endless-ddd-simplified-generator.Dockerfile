# 离线部署时使用，ARM架构aarch64，X86架构x64
ARG CPUARCH=""

FROM redhat/ubi9:9.4-1123${CPUARCH}

LABEL maintainer="Deng Haozhi"

ARG CPUARCH=""
ARG APPNAME="endless-ddd-simplified-generator"
ARG HOME="/${APPNAME}"
ARG JAVA_DIR="-21.0.2"
ENV STARTER="${HOME}/bin/${APPNAME}.sh"

ENV TZ="Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

USER root
RUN mkdir -p ${HOME}/java

ADD ./resource/${APPNAME}.tar.gz ${HOME}
ADD ./resource/openjdk-21.0.2_linux-x64_bin.tar.gz ${HOME}/java

RUN chown -R 10001:10001 ${HOME} && \
    chmod -R 755 ${HOME}
USER 10001

WORKDIR ${HOME}
ENV JAVA_HOME="${HOME}/java/jdk${JAVA_DIR}"
ENV PATH="$JAVA_HOME/bin:$PATH"

# 暴露端口
EXPOSE 10001

# 启动应用
CMD [ "/bin/sh", "-c", "ulimit -n 65536 && ulimit -s 16384 && ${STARTER}" ]
