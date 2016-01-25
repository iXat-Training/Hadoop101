
-----------------------Impala----------------------------------

Impala is a query engine which is integrated into the Hadoop environment and utilizes a number of standard Hadoop components (Metastore, HDFS,  YARN etc...) in order to deliver an RDBMS-like experience in Near Realtime.

Impala was specifically targeted for integration with standard business intelligence  environments, and to that end supports most relevant industry standards: clients can connect via ODBC or JDBC.

There is a JDBC sample in the repo which demonstrates how traditional Java based applications can use Impala and demonstrates its near time capabilities.

Impala has 3 Services.

impalad - The executor's, these are run almost on all data nodes. i.e. there could be many impalad processes in a give cluster.
statestored - One instance of this process is run in the cluster.
catalogd- One instance of this process is run in the cluster.

*impalad* - the Impala daemon service is dually responsible for accepting queries from client processes and orchestrating their execution across the cluster, and for executing individual query fragments on behalf of other Impala daemons.

When an Impala daemon operates in the first role by managing query execution, it is said to be the coordinator for that query. However, all Impala daemons are symmetric; they may all operate in all roles. This property helps with fault-tolerance, and with load-balancing. One Impala daemon is deployed on every machine in the cluster that is also running a datanode process - the block server for the underlying HDFS deployment - and therefore there is typically one Impala daemon on every machine. This allows Impala to take advantage of data locality, and to read blocks from the filesystem without having to use the network. 

*statestored* - The Statestore daemon is Impala’s metadata publish-subscribe service, which disseminates clusterwide metadata to all Impala processes. 

*catalogd* - The Catalog daemon (catalogd), serves as Impala’s catalog repository and metadata access gateway. Through the catalogd, Impala daemons may execute DDL commands that are reflected in external catalog stores such as the Hive Metastore. Changes to the system catalog are broadcast via the statestore.

Refer to the *architecture.png* for data flow and logical cluster topology.




-----------------------Demo----------------------------
Import Cloudera QuickStart VM. We would be using the QuickStart VM for our testing.

Start the VM (this would start all services also)
In the n/w ports of VirtalBox (or any VM provider) configure SSH port for NAT.
Transfer the test data to HDFS of CDH QuickStart VM.

create external table stocks (
stock_name string,trade_date string,hi double,lo double,open double,close double,traded_volume bigint)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' 
LOCATION '/user/cloudera/stocksdata';


create external table stocknames (stock_name string,company_name string)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' 
LOCATION '/user/cloudera/stocknamesdata';

select * from stocks;

select * from stocknames;

select company_name, max(traded_volume), sum(traded_volume) from stocks join stocknames using (stock_name)
group by company_name;

select company_name, stocks.stock_name from stocks join stocknames ;

Open hive and do schema modifications, if you want them to be reflected in impala, do the following in impala shell.

invalidate metadata;





----------In Pig (start via -useHCatalog)
A = LOAD 'stocks' USING org.apache.hive.hcatalog.pig.HCatLoader();
B = FOREACH A Generate stock_name, trade_date, open, $6;
store B into '/user/cloudera/pigstock' USING PigStorage(',');
