import org.apache.spark.sql.AnalysisException

object SimpleStatistics {
	def main(args: Array[String]) {
		
		try {
			val csv_path = args(0)
			
			val fjp_df = spark.read.format("csv")
									.option("sep", ",")
									.option("inferSchema", "true")
									.option("header", "true")
									.option("encoding", "UTF-8")
									.option("quote", "\"")
									.option("escape", "\"")
									.load(csv_path)
			fjp_df.printSchema()
			
			
			fjp_df.cache()
			
			
			val num_lines = fjp_df.count() + 1
			println(s"Number of lines in the csv file: ${num_lines} (${num_lines - 1} for the data and 1 for the header)")
			println("")
			
			val num_fraud = fjp_df.filter($"fraudulent" === "1").count()
			println(s"Number of fake job postings: ${num_fraud}")
			println("")
			
			val num_real = fjp_df.filter($"fraudulent" === "0").count()
			println(s"Number of real job postings: ${num_real}")
			println("")
			
			val top_n_fraudulent = fjp_df.filter($"fraudulent" === "1" && !isnull($"required_education")).groupBy("required_education").count().orderBy($"count".desc).limit(10).select("required_education").collect().map(_(0)).mkString(", ")
			println(s"Top-10 most required education in fake job postings: ${top_n_fraudulent}")
			println("")
			
			val top_n_real = fjp_df.filter($"fraudulent" === "0" && !isnull($"required_education")).groupBy("required_education").count().orderBy($"count".desc).limit(10).select("required_education").collect().map(_(0)).mkString(", ")
			println(s"Top-10 most required education in real job postings: ${top_n_real}")
		}
		catch {
			case e: ArrayIndexOutOfBoundsException => {
				val class_name = this.getClass.getSimpleName.dropRight(1)
				println("Usage: " + class_name + ".main(Array(\"/path/to/dataset/file.csv\"))")
			}
			case e: AnalysisException => {
				println("Wrong CSV file path")
			}
		}
	}
}
