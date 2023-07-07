FROM openjdk:17
EXPOSE 8081
ADD target/friennly-server.jar friennly-server.jar
ENTRYPOINT ["java", "-jar", "friennly-server.jar"]