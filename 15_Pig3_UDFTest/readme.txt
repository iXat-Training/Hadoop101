================================================
Demonstration of creating a UDF in java and using the same in pig
================================================

----Create the UDF in Java
compile the java project using maven

----copy the jar to your CentOS and run the following pig script
register /home/hduser/udf.jar;
define WeekDay com.ixat.pig.UDFTest.WeekDay;

S1 = Load '/stocks/stocks.csv' USING PigStorage(',') AS  (SYMBOL:chararray, DATE:chararray, HI:double, LO:double, OPEN:double, CLOSE:double, TVTRADED:long) ;
S2 = FOREACH S1 Generate SYMBOL, DATE, CLOSE, $6;
S3 = GROUP S2 BY WeekDay(DATE, 'dd-MMM-yyyy');
S4 = FOREACH S3 GENERATE group, SUM( S2.TVTRADED) AS SUMOFTVTRADED;
S5 = ORDER S4 BY SUMOFTVTRADED DESC;
STORE S5 into '/stocks/out2' USING PigStorage(',');

