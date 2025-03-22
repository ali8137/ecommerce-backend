# 1st Stage: Build Stage
FROM maven:3.8.5-openjdk-17 AS build
# the above base image has JDK 17 and Maven
# or
#FROM eclipse-temurin:17-jdk-alpine AS build

# Add a non-root user if needed (depends on the language)

# directory where the COPY and RUN commands will be executed inside the container
WORKDIR /app

# copy only files required to install dependencies (better layer caching)
COPY pom.xml ./

# install dependencies and cache them across builds:
RUN --mount=type=cache,target=/root/.m2  \
    mvn dependency:go-offline

# the below are used if the bad image was "eclipse-temurin:17-jdk-alpine"
## copy only files required to install dependencies (better layer caching)
#COPY mvnw pom.xml ./
#COPY .mvn .mvn
#
## install dependencies and cache them across builds:
#RUN --mount=type=cache,target=/root/.m2  \
#    chmod +x mvnw && ./mvnw dependency:go-offline


# copy application source code
COPY src ./src
# or
#COPY src src

# build the application
RUN mvn clean package -DskipTests
# or
#RUN ./mvnw clean package -DskipTests

############################################

# 2nd Stage: Minimal Deployable Image
FROM eclipse-temurin:17-jre-alpine

# set environment variable for Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod
#ENV SPRING_PROFILES_ACTIVE=docker

# create a non-root user in the minimal image
RUN addgroup -S spring && adduser -S spring -G spring

# directory where the COPY and RUN commands will be executed inside the container
WORKDIR /app

# copy built JAR (fat jar) from the build stage
COPY --from=build /app/target/*.jar app.jar

# use non-root user
USER spring

# expose application port
EXPOSE 8088

# run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]