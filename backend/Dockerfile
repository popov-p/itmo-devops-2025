FROM openjdk:21-jdk-slim AS build

WORKDIR /backend

COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

COPY src src
RUN ./gradlew build

FROM openjdk:21-jdk-slim
WORKDIR /backend

COPY --from=build /backend/build/libs/backend-0.0.1-SNAPSHOT.jar /backend/backend-0.0.1-SNAPSHOT.jar
EXPOSE 8070

CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]