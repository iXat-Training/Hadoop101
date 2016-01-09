Download Drill from Apache - https://drill.apache.org/download/

Extract the file
	 tar xvfz apache-drill-1.4.0.tar.gz
 
Rename the directory
	mv apache-drill-1.4.0 drill

Set the appropriate env variables
	export DRILL_HOME=/home/hduser/drill
	export PATH=$PATH:$DRILL_HOME/bin

copy the sample data (business.json, i have it in /home/hduser/business.json)

Start drill in embdded mode
	drill-embedded

 select * from dfs.`/home/hduser/business.json` limit 10;

 select state, count(review_count) as Reviews from dfs.`/home/hduser/business.json` group by state;


 We could visualize this data in Tableu-
   -Download Drill MapR ODBC driver, instructions here https://drill.apache.org/docs/installing-the-driver-on-windows/

   -Configure Virtual Box port mapping and open 31010 (the port on which drill runs)
   -Configure the driver
   -Download and install Tableu Desktop (there is a 15day trial) http://www.tableau.com/products/trial

   -Create a data source in Tableu and explore the charting options from the data set derived out of drill

