# --- Stage 1: Build stage (Maven + JDK) ---
# Ye stage sirf JAR banane ke liye use hoga
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Source code copy karo
COPY . .

# --- CHANGE: Maven caching ke liye repo path specify karna optional hai ---
# Ye line JAR build karegi aur tests skip karegi
RUN mvn clean package -Dmaven.repo.local=/root/.m2/repository -DskipTests


# --- Stage 2: Runtime stage (JRE only) ---
# Ye final image hogi jo sirf JAR run karegi
FROM eclipse-temurin:21-jre
WORKDIR /app

# Build stage se JAR copy karo
COPY --from=build /app/target/*.jar app.jar

# Port expose karo (service specific port)
EXPOSE 8083

# Entry point set karo
ENTRYPOINT ["java","-jar","app.jar"]

