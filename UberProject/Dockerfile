FROM maven:3.9.6-eclipse-temurin-17
RUN mkdir /app
COPY  . . /app/
WORKDIR /app
RUN mvn clean install
RUN mvn package
CMD ["java", "-jar", "./target/java17-app-1.0.0.jar"]

