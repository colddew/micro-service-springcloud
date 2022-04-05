FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9999
ENTRYPOINT ["java","-jar","/app.jar"]
#ENTRYPOINT ["java","-Dspring.profiles.active=k8s","-jar","/app.jar"]