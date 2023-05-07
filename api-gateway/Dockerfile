FROM openjdk:17-alpine
RUN apk update && apk add busybox-extras
COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway-0.0.1-SNAPSHOT.jar
WORKDIR /app/
CMD ["java", "-jar", "/app/api-gateway-0.0.1-SNAPSHOT.jar"]