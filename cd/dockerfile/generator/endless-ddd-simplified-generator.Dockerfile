FROM bitnami/java:21.0.7-9

LABEL maintainer="Deng Haozhi"

ARG APPNAME="endless-ddd-simplified-generator"
ARG HOME="/${APPNAME}"
ENV STARTER="${HOME}/bin/${APPNAME}.sh"

ENV TZ="Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


ADD ./resource/${APPNAME}.tar.gz ${HOME}

RUN chmod -R 755 ${HOME}

WORKDIR ${HOME}

# 暴露端口
EXPOSE 80

# 启动应用
CMD [ "/bin/sh", "-c", "ulimit -n 65536 && ulimit -s 16384 && ${STARTER}" ]
