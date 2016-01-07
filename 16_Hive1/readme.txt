Hive Reference Manual 
	https://cwiki.apache.org/confluence/display/Hive/LanguageManual

Download Hive from Apache
 On your CentOS
	  wget http://www.eu.apache.org/dist/hive/hive-1.2.1/apache-hive-1.2.1-bin.tar.gz
 
 Extract the tar file
 		tar xvfz apache-hive-1.2.1-bin.tar.gz
 
 Remove the downloaded archive
 		rm apache-hive-1.2.1-bin.tar.gz
 
 Rename the folder for convinience
 	 	mv apache-hive-1.2.1-bin hive

 Optionally add HIVE_HOME and point the variable to /home/hduser/hive

 Also, for convinience modify PATH and add $HIVE_HOME/bin to PATH env variable
 	example in your ~/.bashrc
 	  	export HIVE_HOME=/home/hduser/hive
  		export PATH=$PATH:$HIVE_HOME/bin

 Also, set the below env variable if you see incompatablity issues with classpath
    export HADOOP_USER_CLASSPATH_FIRST=true

 Upload our stocks.csv data file to HDFS under /stocks/stocks.csv 

  open hive cli by typing in hive command, in the hive prompt create the below table.

			create table stocks (
				stock_name string,
				trade_date string,
				hi double,
				lo double,
				open double,
				close double,
				traded_volume bigint)
			ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' ;

  Load our stocks.csv data into the newly created table - 
			LOAD DATA  INPATH '/stocks/stocks.csv' OVERWRITE INTO TABLE stocks;

  Examine what happened to the original file, also observe the new directory /user/hive/warehouse in the HDFS. 			

  Do a select on the table
  			select * from stocks;

  Examine the structure of the table
  			describe stocks;

  Do a select with projection and filtering
  			 select stock_name, trade_date, traded_volume from stocks where stock_name='MSFT';

  Do an aggregation
  			select stock_name, sum(traded_volume) from stocks group by stock_name;
  
  	Observe that the data is ordered on stock_name (the S&S phase has already sorted on key name, which in our case is stock_name)
 
  Do a multi aggregation
 			select stock_name, trade_date, count(traded_volume) from stocks group by stock_name,trade_date;
 	Again observe the sort order and number of map-red's		
  
  Custom sort
 			select stock_name, trade_date, count(traded_volume) from stocks group by stock_name,trade_date order by trade_date desc;
 	Observe the sort order, and number of map-reduce phases

   Custom sort with date conversion -
   	we have trade_date stored as string and hence the sort order was lexicographic, instead lets do a date conversion using inbuilt functions and do a sort again. we use unixtimestamp conversions here

   		select stock_name, from_unixtime(unix_timestamp(trade_date,'dd-MMM-yy')) as tradedate, count(traded_volume) from stocks group by stock_name,from_unixtime(unix_timestamp(trade_date,'dd-MMM-yy')) order by tradedate desc; 

   	Create a stocks_analyzed diamension	
  
		create table stocks_analyzed_tv as select stock_name, sum(traded_volume) from stocks group by stock_name;

	Do a multiple selects on the new diamension.
		