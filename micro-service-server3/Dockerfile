FROM openjdk:8-jdk-alpine
VOLUME /tmp
#ARG JAR_FILE
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8013
#ENTRYPOINT ["java","-jar","/app.jar"]
ENTRYPOINT ["java","-Dspring.profiles.active={SPRING_PROFILES_ACTIVE}","-jar","/app.jar"]