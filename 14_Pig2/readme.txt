--Create a file stocks1.pig with the below content
S1 = Load '/stocks/stocks.csv' USING PigStorage(',') AS  (SYMBOL:chararray, DATE:chararray, HI:double, LO:double, OPEN:double, CLOSE:double, TVTRADED:long) ;
S2 = ORDER S1 BY $sortcolumn DESC;

--run this file using a param
pig -param sortcolumn=TVTRADED  stocks1.pig


--create a file named stocks2.pig with follwing content, this example demonstrates grouping of data
S1 = Load '/stocks/stocks.csv' USING PigStorage(',') AS  (SYMBOL:chararray, DATE:chararray, HI:double, LO:double, OPEN:double, CLOSE:double, TVTRADED:long) ;
S2 = FOREACH S1 Generate SYMBOL, DATE, CLOSE, $6;
S3 = GROUP S2 BY SYMBOL;
S4 = FOREACH S3 GENERATE group, SUM( S2.TVTRADED) AS SUMOFTVTRADED;
STORE S4 into '/stocks/out1' USING PigStorage(',');

---open a new grunt and type the above statements (minus STORE), examine the plan and logical steps performed to derive data for S4 using the below
explain S4;
illustrate S4;



--Create a file stock_meta.csv with following content
MSFT, Microsoft Inc
APPL, Apple Inc
YHOO, Yahoo India Pvt Ltd

--store the file in HDFS
hdfs dfs -put stock_meta.csv /stocks

open grunt

--Lets examine a Join now
stocks = Load '/stocks/stocks.csv' USING PigStorage(',') AS  (SYMBOL:chararray, DATE:chararray, HI:double, LO:double, OPEN:double, CLOSE:double, TVTRADED:long) ;
stocks_meta = Load '/stocks/stock_meta.csv' USING PigStorage(',') AS (SYMBOL:chararray, STOCKNAME:chararray);
S2 = JOIN stocks by SYMBOL , stocks_meta by SYMBOL;
s = GROUP S2 BY stocks_meta::STOCKNAME;
S4 = FOREACH S3 GENERATE TRIM(group), SUM( S2.stocks::TVTRADED ) AS SUMOFTVTRADED;


