FROM openjdk:21

COPY build/libs/AuthenticationMicroservice-0.0.1-SNAPSHOT.jar /app/app2.jar

WORKDIR /app

EXPOSE 8082

CMD ["java", "-jar", "app2.jar"]