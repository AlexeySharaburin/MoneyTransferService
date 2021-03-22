FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 5500

ADD target/transfer_service-0.0.1-SNAPSHOT.jar transfer_service_docker.jar

ENTRYPOINT ["java", "-jar", "/transfer_service_docker.jar"]