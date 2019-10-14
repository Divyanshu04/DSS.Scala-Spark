name := "TESTCASE"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.12" % "2.4.0", "org.apache.spark" % "spark-sql_2.12" % "2.4.0"
)
 
// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.0" % "provided"



