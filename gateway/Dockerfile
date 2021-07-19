FROM openjdk:10
RUN mkdir -p /var/deploy/gateway
WORKDIR var/deploy/gateway
COPY target/gateway-0.0.1-SNAPSHOT.jar ./gateway-latest.jar
CMD ["java", "-jar", "gateway-latest.jar"]