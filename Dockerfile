# Sử dụng image chứa Maven và OpenJDK
FROM maven:3.8.5-openjdk-17
COPY . /app
WORKDIR /app/Common
RUN mvn clean install

# Tiếp tục xây dựng mô-đun "admin"
FROM maven:3.8.5-openjdk-17 as admin-build
WORKDIR /app/Admin
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim AS final
WORKDIR /app
COPY --from=admin-build /app/Admin/target/*.jar admin.jar
EXPOSE 8080
CMD ["java", "-jar", "admin.jar"]
