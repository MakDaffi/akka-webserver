FROM sbtscala/scala-sbt:openjdk-11.0.16_1.7.2_2.13.10

WORKDIR /app

COPY ./src ./src
COPY ./build.sbt ./build.sbt

RUN sbt compile

ENTRYPOINT ["sbt", "run"]

