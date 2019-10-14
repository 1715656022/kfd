FROM registry.cn-beijing.aliyuncs.com/java_god/my_jdk8:test
ARG JAR_FILE
ADD ${JAR_FILE} kdf_web.jar
COPY SIMSUN.ttf /usr/share/fonts/simsun.ttf
EXPOSE 10000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/kdf_web.jar"]