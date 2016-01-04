Download Pig from http://www.eu.apache.org/dist/pig/pig-0.14.0/pig-0.14.0.tar.gz

Check the release note in the tar.gz file for version compatability

Copy the .gz to your CentOS  (or) do a wget http://www.eu.apache.org/dist/pig/pig-0.14.0/pig-0.14.0.tar.gz


-unzip 
tar xzf pig-0.14.0.tar.gz

-rename the folder
mv pig-0.14.0 pig

If JAVA_HOME is not set, set it now
set HADOOP_HOME to appropriate hadoop dir.
set PIG_HOME and point this to the directory where you have unzipped pig



--- example, in my ~/.bashrc i have the following
			export JAVA_HOME=/opt/jdk1.8.0_66
			export JRE_HOME=/opt/jdk1.8.0_66/jre
			export PATH=$PATH:/opt/jdk1.8.0_66/bin:/opt/jdk1.8.0_66/jre/bin

			export HADOOP_HOME=/home/hduser/hadoop
			export HADOOP_INSTALL=$HADOOP_HOME
			export HADOOP_MAPRED_HOME=$HADOOP_HOME
			export HADOOP_COMMON_HOME=$HADOOP_HOME
			export HADOOP_HDFS_HOME=$HADOOP_HOME
			export YARN_HOME=$HADOOP_HOME
			export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
			export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin

	--> For PIG
			export PIG_HOME=/home/hduser/pig

			export PATH=$PATH:$PIG_HOME/bin

-----
source ~/.bashrc

-- prepare data
  transfer stocks.tsv and stocks.csv to your CentOS

--start pig
pig -x local (we are starting pig in local mode)
open grunt prompt 
>S1 = Load '/home/hduser/stocks.tsv'
>DESCRIBE S1;
>DUMP S1;


---Close grunt using CTRL+D, and reopen 

>S1 = Load '/home/hduser/stocks.tsv' as (SYMBOL, DATE, HI, LO, OPEN, CLOSE, TVTRADED) ;
>DESCRIBE S1;
>DUMP S1;

---Close grunt using CTRL+D, reopen, lets do a filter on S1

>S1 = Load '/home/hduser/stocks.tsv' as (SYMBOL, DATE, HI, LO, OPEN, CLOSE, TVTRADED) ;
>S2 = FILTER S1 BY (SYMBOL == 'APPL')
>STORE S2 INTO '/home/hduser/p1';

---Close grunt
---examine the p1 dir


>S1 = Load '/home/hduser/stocks.csv' USING PigStorage(',') AS  (SYMBOL, DATE, HI, LO, OPEN, CLOSE, TVTRADED) ;
>S2 = FILTER S1 BY (SYMBOL == 'APPL');
> DUMP S2;

---Or, create a test.pig file with the below content
S1 = Load '/home/hduser/stocks.csv' USING PigStorage(',') AS  (SYMBOL, DATE, HI, LO, OPEN, CLOSE, TVTRADED) ;
S2 = FILTER S1 BY (SYMBOL == 'APPL');
DUMP S2;

---execute the file using local mode
pig -x local test.pig




