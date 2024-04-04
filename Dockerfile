# Sử dụng image chứa Maven và OpenJDK
FROM maven:3.8.5-openjdk-17 AS admin-build

# Copy mã nguồn ứng dụng vào container
COPY . /app

# Thiết lập thư mục làm việc
WORKDIR /app

# Xây dựng mô-đun "common" trước
WORKDIR /app/Common
RUN mvn clean install

# Tiếp tục xây dựng mô-đun "admin"
WORKDIR /app/Admin
RUN mvn clean package -DskipTests

# Xây dựng mô-đun "client"
WORKDIR /app/Client
RUN mvn clean package -DskipTests

# Expose cổng mặc định của Spring Boot (8080 và 8081)
EXPOSE 8080
EXPOSE 8081

# Chạy cả Admin và Client khi container được khởi chạy
CMD ["sh", "-c", "java -jar /app/Admin/target/Admin-0.0.1-SNAPSHOT.jar & java -jar /app/Client/target/Client-0.0.1-SNAPSHOT.jar"]
