name := "MagineTest"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq("net.liftweb" %% "lift-json" % "2.6+",
                            "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
                             "org.scalactic" %% "scalactic" % "3.0.0"
                             ,"org.scalatest" %% "scalatest" % "3.0.0" % "test")