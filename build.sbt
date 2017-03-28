name := "ADThena"

version := "1.0"    

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.github.nscala-time" %% "nscala-time" % "2.16.0",
  "org.scalaz" %% "scalaz-core" % "7.2.9",
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
