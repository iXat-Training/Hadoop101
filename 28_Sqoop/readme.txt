Create a Table in a DB of your choice
Copy the appropriate JDBC Driver of the choosen DB to the lib folder of sqoop, sqoop uses JDBC when it connects to an RDBMS and hence the appropriate DriverManager should be available for sqoop. 


Have HDFS+YARN up and running.

Import the table, by running two mapper's (parallelization to do a faster import)
	sqoop import  --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --table stocks_mysql -m 2

Import the table, to a specific dir in HDFS
	sqoop import  --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --table stocks_mysql --target-dir "/stocksnew"

Import the table, to a specific dir in HDFS, delete the target dir if that already exists
	sqoop import  --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --table stocks_mysql --target-dir "/stocksnew" --delete-target-dir


Import the table, and project only a specific set of columns, to a specific dir in HDFS
	sqoop import  --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --target-dir "/stocksnew" --query "select TID,STOCK_NAME,TRADED_DATE from stocks_mysql WHERE \$CONDITIONS "   --delete-target-dir -m 1

	Note: You need $CONDITIONS in there because sqoop queries the database  about column type information, etc in the client before executing the  import job, but does not want actual rows returned to the client. So  it will execute your query with $CONDITIONS set to '1 = 0' to ensure  that it receives type information, but not records. Once the type information is available to sqoop, then the user query is fired without the 1=0 notation

Import the table, only a subset of rows by applying a query filter, to a specific dir in HDFS, but in multiple mappers.
	 sqoop import  --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --target-dir "/stocksnew" --query "select TID,STOCK_NAME,TRADED_DATE from stocks_mysql WHERE \$CONDITIONS AND STOCK_NAME='MSFT' "   --delete-target-dir -m 2 --split-by TID

	 Note: Observe the usage of $CONDITIONS and the actual filter query.
	 	   Also, note that sqoop need to be supplied by --split-by to let it know on how the data needs to be splitted for parallelization.


Do an incremental import from those rows which carry TID value > 800. Note the set of files created after the import
	 sqoop import  --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --target-dir "/stocksnew" --query "select TID,STOCK_NAME,TRADED_DATE from stocks_mysql WHERE \$CONDITIONS AND STOCK_NAME='MSFT' "   --delete-target-dir --incremental append --check-column TID --last-value 800 -m 1


Do you want to do this every time? then create a Job, we create a Job with a name "J1". Note the space between -- and import.
	 sqoop job --create J1 -- import --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --target-dir "/stocksnew" --query "select TID,STOCK_NAME,TRADED_DATE from stocks_mysql WHERE \$CONDITIONS AND STOCK_NAME='MSFT' " --incremental append --check-column TID --last-value 800 -m 1

Get all the jobs that are stored	 
	 sqoop job --list

examine the contents of a Job that is saved earlier
	 sqoop job --show J1

Run the job
	sqoop job --exec J1

	From Sqoop Documentation:
		Incremental imports are performed by comparing the values in a check column against a reference value for the most recent import. For example, if the --incremental append argument was specified, along with --check-column id and --last-value 100, all rows with id > 100 will be imported. If an incremental import is run from the command line, the value which should be specified as --last-value in a subsequent incremental import will be printed to the screen for your reference. If an incremental import is run from a saved job, this value will be retained in the saved job. Subsequent runs of sqoop job --exec someIncrementalJob will continue to import only newer rows than those previously imported.

If you want to examine the actual writable that sqoop generated and used for above Map jobs, 
	sqoop codegen  --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --table stocks_mysql 

	the above command would create a Jar file which has the writable implementation.


Use eval if you want to quickly evaluate if the query looks good at DB side before triggering the Job, and hence observe the $CONDITIONS is not available in this case.
	sqoop eval  --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --query "select TID,STOCK_NAME,TRADED_DATE from stocks_mysql WHERE STOCK_NAME='MSFT' limit 2"




Lets do a fresh import of all the data from stocks_mysql to /stocksdata

	sqoop import  --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --table stocks_mysql --target-dir "/stocksdata" -m 2
	
Lets run some pig analytics on the data 
	S1 = Load '/stocksdata/part-m*' USING PigStorage(',') AS  (SYMBOL:chararray, TID:int, DATE:chararray, HI:double, LO:double, OPEN:double, CLOSE:double, TVTRADED:long) ;
	S2 = FOREACH S1 Generate SYMBOL, DATE, CLOSE, $7;
	S3 = GROUP S2 BY SYMBOL;
	S4 = FOREACH S3 GENERATE group, SUM( S2.TVTRADED) AS SUMOFTVTRADED;
	STORE S4 into '/stocksanalyzed/out1' USING PigStorage(',');

Examine the analyzed content
	hdfs dfs -cat /stocksanalyzed/out1/pa*

The output is in a format as below
	APPL,12998553230
	MSFT,8972070740
	YHOO,3949293759

Lets now export this data to MySQL, for that create a table in MYSQL which adheres to the HDFS file structure (sqoop will not create the target table)

At mysql prompt (or in any mysql client)
	USE hdptests;
	 CREATE TABLE stocks_trade ( 
	   id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	   stock_name VARCHAR(20), 
	   trade_volume BIGINT);

sqoop export  --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --table stocks_trade  --columns stock_name,trade_volume --export-dir /stocksanalyzed/out1 -m 1

Verify if the analyzed content has come to mysql, if any errors use Yarn RM UI to troubleshoot