
FROM sourcemation/jdk-21 AS build
WORKDIR /app


COPY pom.xml .
COPY src src
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests


FROM sourcemation/jdk-21
WORKDIR /app
VOLUME /tmp

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
