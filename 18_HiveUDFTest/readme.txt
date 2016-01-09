Create a UDF
Compile the UDF using Maven
Copy the JAR to HDFS
	hdfs dfs -mkdir /jars
	hdfs dfs -put myudf.jar /jars


--Start hive server
		hiveserver2 &

--start the client	
		beeline -u jdbc:hive2://localhost:10000

--create a function
	CREATE FUNCTION WeekDay AS 'com.ixat.hive.HiveUDFTest.WeekDayUDF' USING JAR 'hdfs://localhost:9000/jars/myudf.jar';

---Use the FUNCTIOn
	select stock_name, WeekDay(trade_date) from stocks;
