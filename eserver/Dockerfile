FROM openjdk:10
RUN mkdir -p /var/deploy/eserver
WORKDIR var/deploy/eserver
COPY build/libs/eserver-0.0.1-SNAPSHOT.jar ./eserver-latest.jar
CMD ["java", "-jar", "eserver-latest.jar"]