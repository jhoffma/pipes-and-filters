import sbt._

object Dependencies {
  lazy val service = Compile.dependencies ++ TestUtils.dependencies

  object Compile {
    lazy val dependencies = Seq(akkaActor, akkaStream, apacheCommonCodec)

    val akkaActor = "com.typesafe.akka" %% "akka-actor" % "2.4.17"
    val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.4.17"
    val apacheCommonCodec = "commons-codec" % "commons-codec" % "1.9"
  }

  object TestUtils {
    lazy val dependencies = Seq(scalaTest)

    val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  }
}