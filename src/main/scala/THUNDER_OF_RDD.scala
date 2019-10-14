import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}

object THUNDER_OF_RDD{

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("coalesce checking")
      .master("local[*]")
      .getOrCreate()


    val file = spark.sparkContext.textFile("/Users/divyanshushekharsingh/jsonproject/file.csv", 2)
    val file1 = file.map(_.charAt(5).toUpper)
    file.foreach(println)
    file1.foreach(println)
    file.partitions.size


    //Some acclaimed guidelines for the number of partitions in Spark are as follows-
    //When the number of partitions is between 100 and 10K partitions based on the size of the cluster and data, the lower and
    // upper bound should be determined.
    //The lower bound for spark partitions is determined by 2 X number of cores in the cluster available to application.
    //Determining the upper bound for partitions in Spark, the task should take 100+ ms time to execute. If it takes less time,
    // then the partitioned data might be too small or the application might be spending extra time in scheduling tasks.
    //****>>>https://www.dezyre.com/article/how-data-partitioning-in-spark-helps-achieve-more-parallelism/297
    //******************************

    //https://stackoverflow.com/questions/29383578/how-to-convert-rdd-object-to-dataframe-in-spark
    //********

    val r1: RDD[Row] = spark.sparkContext.parallelize(
      Seq(
        Row("first", 2.0, 7.0),
        Row("second", 3.5, 2.5),
        Row("third", 7.0, 5.9)
      )
    )


    //val sqlContext = new sqlContext(spark)

   // r1.toDF()

  }
}