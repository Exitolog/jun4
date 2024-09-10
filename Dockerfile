FROM openjdk:21
COPY out/artifacts/junior4_homework_jar/junior4_homework.jar /tmp/junior4_homework.jar
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "/tmp/junior4_homework.jar"]