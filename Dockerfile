FROM openjdk:8-jdk-alpine3.7


ADD /target/JOKRs.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","/app.jar"]
