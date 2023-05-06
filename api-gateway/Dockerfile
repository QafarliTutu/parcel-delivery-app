FROM alpine:3.16.0
RUN apk add --no-cache java-cacerts openjdk17-jdk
COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway-0.0.1-SNAPSHOT.jar
WORKDIR /app/
CMD ["java", "-jar", "/app/api-gateway-0.0.1-SNAPSHOT.jar"]