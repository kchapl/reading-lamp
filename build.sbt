lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name         := "reading-lamp",
    version      := "1.0",
    scalaVersion := "3.3.4",
    libraryDependencies ++= Seq(
      guice,
      "com.google.oauth-client" % "google-oauth-client"         % "1.37.0",
      "com.google.http-client"  % "google-http-client-jackson2" % "1.45.3",
      "org.scalatestplus.play" %% "scalatestplus-play"          % "7.0.1" % Test,
      "com.google.api-client"   % "google-api-client"           % "2.0.1",
      "com.google.oauth-client" % "google-oauth-client-jetty"   % "1.37.0",
      "com.google.apis"         % "google-api-services-oauth2"  % "v2-rev20200213-2.0.0"
    ),
    // Add Play specific settings
    PlayKeys.playDefaultPort := 9000
  )
