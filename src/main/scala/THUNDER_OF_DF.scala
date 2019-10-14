import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.{Row, SparkSession}
object THUNDER_OF_DF {

  case class MyClass(val1: String,val2:Double,val3:Double)
  case class temp(val1: String,val3 : Double)
  case class RLT (id: String, attrib_1 : String, attrib_2 : String)
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("coalesce checking")
      .master("local[*]")
      .getOrCreate()
    //**********************************************************
    /*
    //convert rdd to DF
    // first

    val r1= spark.sparkContext.parallelize(
    Seq(
    ("first", Array(2.0, 1.0, 2.1, 5.4)),
    ("test", Array(1.5, 0.5, 0.9, 3.7)),
    ("choose", Array(8.0, 2.9, 9.1, 2.5))
    )
    )
    val dfWithoutSchema = spark.createDataFrame(r1)

    dfWithoutSchema.printSchema()

    dfWithoutSchema.show()

    //second
    val dfWithSchema = spark.createDataFrame(r1).toDF("Name", "values")

    dfWithSchema.show()
    //Method 3 (Actual answer to question)

    val rowsRdd: RDD[Row] = spark.sparkContext.parallelize(
    Seq(
    Row("first", 2.0, 7.0),
    Row("second", 3.5, 2.5),
    Row("third", 7.0, 5.9)
    )
    )

    val schema = new StructType().add(StructField("id",StringType,true))
    .add(StructField("val1",DoubleType,true))
    .add(StructField("val2",DoubleType,true))
    val df = spark.createDataFrame(rowsRdd,schema)
    df.show()
   */
    //****************************************************************
    //*************************************************
    //@NOte:To create a DataFrame from an RDD of Rows, there are two main options:

    // 1) As already pointed out, you could use toDF() which can be imported by import spark.implicits._. However,
    // this approach only works for the following types of RDDs:
    //https://stackoverflow.com/questions/29383578/how-to-convert-rdd-object-to-dataframe-in-spark
    // RDD[Int]
    // RDD[Long]
    // RDD[String]
    // RDD[T <: scala.Product]
    //The last signature actually means that it can work for an RDD of tuples or an RDD of case classes (because tuples and case classes are subclasses of scala.Product).
    //So, to use this approach for an RDD[Row], you have to map it to an RDD[T <: scala.Product]. This can be done by mapping each row to a custom case class or to a tuple, as in the following code snippets:
    import spark.implicits._
    val rdd: RDD[Row] = spark.sparkContext.parallelize(
      Seq(
        Row("first", 2.0, 7.0),
        Row("second", 3.5, 2.5),
        Row("third", 7.0, 5.9)
      )
    )
    // 1 st way
    val df = rdd.map({
      case Row(val1: String,val2:Double,val3:Double) => (val1,val2,val3)
    }).toDF("name","name2","name3")
    df.show()

    //2nd way
    val df1 = rdd.map({
      case Row(val1: String,val2:Double,val3:Double) =>MyClass (val1,val2,val3)
    }).toDF("col1_name","col2_name","col3_name")

    df1.show()

    //3rd way (older method to create DF by using older schema detail)
    //You can use createDataFrame(rowRDD: RDD[Row], schema: StructType) as in the accepted answer,
    // which is available in the SQLContext object.
    //convert old df to rdd
    val rdd1 = df1.rdd
    rdd1.foreach(println)
    val newDF = df1.sqlContext.createDataFrame(rdd1, df1.schema)
    newDF.show()
    //**********************************************************************
    //Working on this concept................................................
    //Suppose you have a DataFrame and you want to do some modification on
    // the fields data by converting it to RDD[Row]
    // val aRdd = df1.map(x=>Row(x.getAs[Long]("col1_name"),x.getAs[List[String]]("col2_name").head))
    // aRdd.show()
    // aRdd.printSchema()
    //To convert back to DataFrame from RDD we need to define the structure type of the RDD.
    //
    //If the datatype was Long then it will become as LongType in structure.
    //
    //If String then StringType in structure.

    // val aStruct = new StructType(Array(StructField("col1_name",LongType,nullable = true),StructField("col2_name",StringType,nullable = true)))
    // val aNamedDF = spark.sqlContext.createDataFrame(aRdd,aStruct)
    //*******************************************************************
    // normal creation
    val numlist = List(1,2,3,4,5)
    val x1 = spark.sparkContext.parallelize(numlist)
    val x2 = x1.toDF()
    x2.show()


    // ***********
    //Scala method 1:
    val df_2 = spark.sparkContext.parallelize(Seq((1L, 3.0, "a"), (2L, -1.0, "b"), (3L, 0.0, "c"))).toDF("x", "y", "z")
    df_2.show()

    //scala method 2:
    val y1 = spark.sparkContext.parallelize(Seq(
      Row("HII", 0.5), Row("Divyanshu", 0.0)
    ))
    val rows = y1.map({case Row(val1:String,val3:Double) => temp(val1,val3)}).toDF()
    rows.show()

    //
    val dfSchema = Seq("col1", "col2", "col3")
   // rdd.toDF(dfSchema: _*)


  }
}