# 指定一个基础镜像java.7
FROM  java:7
#VOLUME /tmp创建/tmp目录并持久化到Docker数据文件夹，因为Spring Boot使用的内嵌Tomcat容器默认使用/tmp作为工作目录
VOLUME /tmp
#ADD spring-boot-docker-0.1.0.jar app.jar 将应用jar包复制到/app.jar
ADD target/helper.jar /app.jar
#Dokcerfile中的命令每执行一条即产生一个新的镜像，当前命令总是在最新的镜像上执行
RUN bash -c 'touch /app.jar'
CMD ["java -server -Dspring.profiles.active=chengql -jar /app.jar"]
