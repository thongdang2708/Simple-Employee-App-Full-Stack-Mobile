FROM amazoncorretto:17

ARG JAR_FILE=/target/*.jar
COPY ./target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app.jar"]