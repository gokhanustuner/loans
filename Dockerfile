FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/loans-0.0.1-SNAPSHOT.jar /app/loans.jar

ENTRYPOINT ["java", "-jar", "loans.jar"]
