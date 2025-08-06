FROM openjdk:17

LABEL maintainer="rihan@test.com"

WORKDIR /app

COPY target/user-registration-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 3030

ENTRYPOINT ["java", "-jar", "app.jar"]
