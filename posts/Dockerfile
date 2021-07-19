FROM openjdk:10
RUN mkdir -p /var/deploy/posts
WORKDIR var/deploy/posts
COPY build/libs/posts-0.0.1-SNAPSHOT.jar ./posts-latest.jar
CMD ["java", "-jar", "posts-latest.jar"]