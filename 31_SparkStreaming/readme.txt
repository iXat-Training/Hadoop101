Download spark from http://spark.apache.org/downloads.html
Unzip the downloaded archive and set path etc...

run the word count sample using
	spark-shell -i SSWordCount.scala

From an other prompt do a HDFS file copy to hdfs://localhost:9000/streamdir/ and observer the word count in action.

Also, observe what happens if you do -put / -copyFromLocal / -cp

