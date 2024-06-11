lazy val akkaHttpVersion = sys.props.getOrElse("akka-http.version", "10.6.3")
lazy val akkaVersion    = "2.9.3"

resolvers += "Akka library repository".at("https://repo.akka.io/maven")

fork := true

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.13.13"
    )),
    name := "akka-passport-app",
    libraryDependencies ++= Seq(
      "com.typesafe.akka"          %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka"          %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka"          %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka"          %% "akka-stream"              % akkaVersion,
      "ch.qos.logback"             % "logback-classic"           % "1.2.11",
      "com.typesafe.scala-logging" %% "scala-logging"            % "3.9.4",
      "com.typesafe"               %  "config"                   % "1.4.3",
      "org.postgresql"             % "postgresql"                % "42.5.4",

      "com.github.sbt" % "junit-interface" % "0.13.3" % "test",
      "com.softwaremill.sttp.client4" %% "core" % "4.0.0-M16"
    )
  )
