FROM gradle AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean shadowJar --no-daemon

FROM adoptopenjdk:14-jre-openj9
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/service.jar
CMD ["java", "-jar", "/app/service.jar"]