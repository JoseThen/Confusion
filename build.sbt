import play.Project._

name := """Confusion"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  "org.webjars" %% "webjars-play" % "2.2.2",
  "mysql" % "mysql-connector-java" % "5.1.36",
  "org.webjars" % "bootstrap" % "3.3.5")

playJavaSettings
