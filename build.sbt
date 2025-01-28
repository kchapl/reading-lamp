lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
name    := "reading-lamp",
version := "1.0",
scalaVersion := "3.3.4",
    libraryDependencies ++= Seq(
      guice,
      "com.google.oauth-client" % "google-oauth-client" % "1.34.1",
      "com.google.http-client" % "google-http-client-jackson2" % "1.43.3",
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
    ),
    // Add Play specific settings
    PlayKeys.playDefaultPort := 9000
  )
