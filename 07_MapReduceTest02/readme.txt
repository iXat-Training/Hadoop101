Create a Directory /stocks in HDFS,
if on windows and targetting another linux host, set the linux user as a system property using the following env variable
	set HADOOP_OPTS="-DHADOOP_USER_NAME=hduser"

hdfs dfs -mkdir /stocks

##Transfer the stocks csv content to the HDFS
hdfs dfs -copyFromLocal stocks.csv /stocks/stocks_2014-2015.csv

#compile the code using maven (set M2_HOME and PATH appropriately)
#cd to the project directory (the directory where pom.xml is located)
mvn clean install

#transfer the generated jar (locate the jar in target folder) to the centos system

#Run the job
yarn jar 07_MapReduceTest02-1.0.jar com.ixat.stocksapp._MapReduceTest02.App

##If you want to check the logs generated from the above run
yarn logs -applicationId <pick the applicationid from the previous run>


