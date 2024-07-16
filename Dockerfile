FROM gradle:8.8-jdk17 as builder
WORKDIR /app
COPY ./src ./src
COPY ./build.gradle .
COPY ./settings.gradle .
RUN gradle bootJar

FROM openjdk:17-jdk-alpine as extracter
EXPOSE 8080
WORKDIR /app
COPY --from=builder app/build/libs/*.jar .
ENTRYPOINT ["java", "-jar", "otus-social-network-0.0.1-SNAPSHOT.jar"]
