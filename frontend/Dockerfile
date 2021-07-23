FROM openjdk:10
RUN mkdir -p /var/deploy/frontend
WORKDIR var/deploy/frontend
COPY build/libs/frontend-0.0.1-SNAPSHOT.jar ./frontend-latest.jar
CMD ["java", "-jar", "frontend-latest.jar"]