# Stage 1: Build Admin module
FROM maven:3.8.5-openjdk-17 AS admin-build
COPY Admin/ .
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim
COPY --from=admin-build  /app/target/*.jar admin.jar
EXPOSE 8080
CMD ["java", "-jar", "admin.jar"]
