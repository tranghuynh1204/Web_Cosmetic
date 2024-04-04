# Stage 1: Build Common module
FROM maven:3.8.5-openjdk-17 AS common-build
WORKDIR /app/Common
COPY Common .
RUN mvn clean package -DskipTests

# Stage 2: Build Client module
FROM maven:3.8.5-openjdk-17 AS client-build
WORKDIR /app/Client
COPY Client .
RUN mvn clean package -DskipTests

# Stage 3: Build Admin module
FROM maven:3.8.5-openjdk-17 AS admin-build
WORKDIR /app/Admin
COPY Admin .
RUN mvn clean package -DskipTests

# Stage 5: Create final image
FROM openjdk:17-jdk-slim AS final
WORKDIR /app
COPY --from=common-build /app/Common/target/*.jar common.jar
COPY --from=client-build /app/Client/target/*.jar client.jar
COPY --from=admin-build /app/Admin/target/*.jar admin.jar
EXPOSE 8080
CMD ["java", "-jar", "admin.jar"]
