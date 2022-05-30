FROM maven:3.8.5-openjdk-11

COPY . /home/app
WORKDIR /home/app
USER root

RUN mvn clean install

WORKDIR /home/app/target

ENTRYPOINT ["java", "-jar", "kafka-example-kotlin-0.0.1-SNAPSHOT.jar"]
