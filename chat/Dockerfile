FROM openjdk:10
RUN mkdir -p /var/deploy/chat
WORKDIR /var/deploy/chat
COPY build/libs/chat-0.0.1-SNAPSHOT.jar ./chat-latest.jar
CMD ["java", "-jar", "chat-latest.jar"]