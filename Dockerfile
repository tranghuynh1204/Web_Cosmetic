# Stage 1: Build Admin module
FROM maven:3.8.5-openjdk-17 AS admin-build
WORKDIR /app/admin
COPY Admin /app/admin
RUN mvn -f /app/admin/pom.xml clean package -DskipTests

# Stage 2: Build Client module
FROM maven:3.8.5-openjdk-17 AS client-build
WORKDIR /app/client
COPY Client /app/client
RUN mvn -f /app/client/pom.xml clean package -DskipTests

# Stage 3: Create final Docker image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=admin-build /app/admin/target/*.jar admin.jar
COPY --from=client-build /app/client/target/*.jar client.jar
COPY Common /app/common
EXPOSE 8080
CMD ["java", "-jar", "admin.jar"]
