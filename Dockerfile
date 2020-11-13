FROM openjdk:12-alpine
ARG JAR_FILE=target/*.jar
WORKDIR /App
COPY ${JAR_FILE} app.jar
CMD ["java","-jar","/App/app.jar"]
