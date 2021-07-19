FROM openjdk:10
RUN mkdir -p /var/deploy/userprofile
WORKDIR var/deploy/userprofile
COPY build/libs/userprofile-0.0.1-SNAPSHOT.jar ./userprofile-latest.jar
CMD ["java", "-jar", "userprofile-latest.jar"]