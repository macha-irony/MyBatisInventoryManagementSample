FROM eclipse-temurin:21-jdk-alpine
# JARをコピーして実行
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]