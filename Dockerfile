FROM openjdk:17-jdk-slim as build
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN apt-get update && apt-get install dos2unix
RUN dos2unix mvnw
RUN chmod +x ./mvnw
RUN ./mvnw clean install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENV TZ=Asia/Almaty

ENTRYPOINT ["sh", "-c","java $JAVA_OPTS -XX:+UseParallelGC -cp app:app/lib/* kz.corwin.users.UsersApplication"]
