# 1. Maven을 사용하여 프로젝트 빌드
FROM openjdk:20-slim AS build

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 프로젝트 파일 복사
COPY pom.xml ./
COPY src ./src

# 4. 의존성 다운로드 및 프로젝트 빌드
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

# 5. 실행 단계의 이미지 설정
FROM openjdk:20-slim

# 6. 작업 디렉토리 설정
WORKDIR /app

# 7. 빌드된 JAR 파일 복사
COPY --from=build /app/target/Find_Pharn_Near_Me-1.0-SNAPSHOT.jar app.jar

# 8. 애플리케이션 실행
CMD ["java", "-jar", "app.jar"]
