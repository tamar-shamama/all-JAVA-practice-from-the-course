FROM openjdk:11-jre-slim-buster
COPY ./target/2022-10-07-docker-0.0.1-SNAPSHOT.jar /user/app/
WORKDIR /user/app
RUN sh -c 'touch 2022-10-07-docker-0.0.1-SNAPSHOT.jar'
ENTRYPOINT [ "java", "-jar", "2022-10-07-docker-0.0.1-SNAPSHOT.jar" ]