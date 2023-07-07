
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17
COPY --from=build target/friennly-server.jar friennly-server.jar
# ENV PORT=8081
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "friennly-server.jar"]