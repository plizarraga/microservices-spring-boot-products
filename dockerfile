# FROM amazoncorretto:17-alpine

# COPY target/*.jar app.jar

# EXPOSE 8090

# ENTRYPOINT [ "java", "-jar", "app.jar" ]


# FROM maven:3-eclipse-temurin-11 AS build
# COPY src /home/app/src
# COPY pom.xml /home/app
# RUN mvn -f /home/app/pom.xml clean package

# Build Stage
FROM maven:3.8.3-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /home/app

# Copy only the POM file to leverage Docker cache for dependencies
COPY pom.xml .

# Download the project dependencies (cached if pom.xml is unchanged)
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src /home/app/src

# Build the application
RUN mvn package -DskipTests

FROM amazoncorretto:17-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]