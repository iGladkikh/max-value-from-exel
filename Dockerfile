FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY tmp/sample.xlsx .
ENTRYPOINT ["java","-jar","/app/app.jar"]