FROM alpine:3.16.0
RUN apk add --no-cache java-cacerts openjdk17-jdk
COPY build/libs/ms-order-0.0.1-SNAPSHOT.jar /app/ms-order-0.0.1-SNAPSHOT.jar
WORKDIR /app/
CMD ["java", "-jar", "/app/ms-order-0.0.1-SNAPSHOT.jar"]