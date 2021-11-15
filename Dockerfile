FROM openjdk:11.0.13-oraclelinux8

WORKDIR /app
RUN mkdir data

COPY target/demo-0.0.1-SNAPSHOT.jar /app/app.jar
COPY src/test/resources/fixtures/ordered-words-test-set /app/data/ordered-words-test-set

EXPOSE 8080

RUN groupadd -r rambo && useradd -g rambo rambo
RUN chown -R rambo:rambo /app
USER rambo

VOLUME /app/data

ENV WORDS_FILE_PATH=data/ordered-words-test-set

ENTRYPOINT ["java", "-jar", "app.jar"]
