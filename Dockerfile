# Stage 1: Build Admin module
FROM maven:3.8.5-openjdk-17 AS admin-build
WORKDIR /app/admin
COPY Admin /app/admin
RUN mvn -f /app/admin/pom.xml clean package -DskipTests


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=admin-build /app/admin/target/*.jar admin.jar
EXPOSE 8080
CMD ["java", "-jar", "admin.jar"]
