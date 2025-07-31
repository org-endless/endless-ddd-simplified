# 离线部署时使用，ARM架构aarch64，X86架构x64
ARG CPUARCH=""

FROM registry.access.redhat.com/ubi9/openjdk-21:1.16

LABEL maintainer="Deng Haozhi"

ARG CPUARCH=""
ARG APPNAME="endless-ddd-simplified-generator"
ARG HOME="/${APPNAME}"
ENV STARTER="${HOME}/bin/${APPNAME}.sh"

ENV TZ="Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD ./resource/${APPNAME}.tar.gz ${HOME}

WORKDIR ${HOME}

# 暴露端口
EXPOSE 60001

# 启动应用
CMD [ "/bin/sh", "-c", "ulimit -n 65536 && ulimit -s 16384 && ${STARTER}" ]
