# 指定一个基础镜像centos
FROM docker.io/centos:latest
#安装应用执行的环境java
RUN yum -y install java
#将指定的jar文件复制到容器中
COPY helper.jar /usr/local/
#执行jar文件
ENTRYPOINT ["java" ,"-jar","/usr/local/helper.jar"]