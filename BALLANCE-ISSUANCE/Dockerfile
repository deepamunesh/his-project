FROM eclipse-temurin:17-jdk
WORKDIR /app
# --- FIX: Use wildcard to avoid JAR name mismatch ---
COPY target/*.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","app.jar"]
