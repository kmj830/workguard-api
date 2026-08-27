# ==========================================
# 1. Build Stage
# ==========================================
FROM eclipse-temurin:25-jdk-alpine AS builder

WORKDIR /app

# Gradle Wrapper 및 빌드 스크립트 복사 (의존성 캐싱 레이어)
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

# Gradle 실행 권한 부여 및 의존성 사전 다운로드
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon

# 소스 코드 복사 및 애플리케이션 빌드
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test && \
    mv $(ls build/libs/*.jar | grep -v 'plain') /app/app.jar

# ==========================================
# 2. Run Stage
# ==========================================
FROM eclipse-temurin:25-jre-alpine AS runner

WORKDIR /app

# 보안을 위한 non-root 사용자 및 그룹 생성
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 빌드 스테이지에서 생성된 fat jar 파일 복사
COPY --from=builder /app/app.jar app.jar

# 파일 소유권 변경
RUN chown -R appuser:appgroup /app

# non-root 사용자로 전환
USER appuser

# Cloud Run 기본 포트 및 Spring 프로파일 환경변수 설정
ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

# 컨테이너 메모리 최적화 옵션 및 Cloud Run PORT 환경변수 바인딩
ENTRYPOINT ["sh", "-c", "exec java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT:-8080} -jar app.jar"]
