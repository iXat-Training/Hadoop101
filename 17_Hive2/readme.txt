Start hiveserver
	hiveserver2 &

change permissions on hdfs
	hdfs dfs -chmod -R 0777 /tmp
	hdfs dfs -chmod -R 0777 /user/hive


start the V2 cli
	beeline -u jdbc:hive2://localhost:10000


---pre create the stocks table and insert the sample data into it

--- now create a partiotioned table
create table stocks_p (
trade_date string,
hi double,
lo double,
open double,
close double,
traded_volume bigint)
PARTITIONED BY (stock_name string) 
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' ;

INSERT INTO TABLE stocks_p PARTITION (stock_name='MSFT')  select trade_date, hi, lo, open, close, traded_volume from stocks where stock_name = 'MSFT';
INSERT INTO TABLE stocks_p PARTITION (stock_name='APPL')  select trade_date, hi, lo, open, close, traded_volume from stocks where stock_name = 'APPL';
INSERT INTO TABLE stocks_p PARTITION (stock_name='YHOO')  select trade_date, hi, lo, open, close, traded_volume from stocks where stock_name = 'YHOO';

select * from stocks_p;
select * from stocks_p where stock_name='MSFT';


---examine the warehouse structure

---turn on clustering
---lets create table with clustering and partitioning

set hive.enforce.bucketing = true;

create table stocks_cp (
trade_date string,
hi double,
lo double,
open double,
close double,
traded_volume bigint)
PARTITIONED BY (stock_name string) 
CLUSTERED BY (trade_date) INTO 4 BUCKETS
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' ;

INSERT INTO TABLE stocks_cp PARTITION (stock_name='MSFT')  select trade_date, hi, lo, open, close, traded_volume from stocks where stock_name = 'MSFT';
INSERT INTO TABLE stocks_cp PARTITION (stock_name='APPL')  select trade_date, hi, lo, open, close, traded_volume from stocks where stock_name = 'APPL';
INSERT INTO TABLE stocks_cp PARTITION (stock_name='YHOO')  select trade_date, hi, lo, open, close, traded_volume from stocks where stock_name = 'YHOO';

---examine the warehouse structure

---now lets create a RC file instead of text

create table stocks_rc (
trade_date string,
hi double,
lo double,
open double,
close double,RCFILE
traded_volume bigint)
PARTITIONED BY (stock_name string) 
CLUSTERED BY (trade_date) INTO 4 BUCKETS
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'
STORED AS RCFILE


---examine the warehouse structure