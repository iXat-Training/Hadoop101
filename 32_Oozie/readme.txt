Oozie is a workflow scheduler system to manage Apache Hadoop jobs.

Oozie Workflow jobs are Directed Acyclical Graphs (DAGs) of actions.

Oozie Coordinator jobs are recurrent Oozie Workflow jobs triggered by time (frequency) and data availabilty.

Oozie is integrated with the rest of the Hadoop stack supporting several types of Hadoop jobs out of the box (such as Java map-reduce, Streaming map-reduce, Pig, Hive, Sqoop and Distcp) as well as system specific jobs (such as Java programs and shell scripts).

Oozie is a scalable, reliable and extensible system.

Unfortunately Oozie binaries are not distributed unlike others, we need to build Oozie binaries on owr own (or use CDH VM etc..)

Steps to build Oozie
-----------------------
Choose a Linux based system
Install zip and unzip (as sudo/roo)  
	yum install unzip zip -y

Install Maven
	wget http://www.eu.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
	tar xvfz apache-maven-3.3.9-bin.tar.gz
	mv apache-maven-3.3.9 maven
	
	Modify your env (bashrc) to include M2_HOME to maven dir and also set bin to M2_HOME/bin
	echo "export M2_HOME=`pwd`/maven" >> ~/.bashrc
	echo 'export PATH=$PATH:$M2_HOME/bin' >> ~/.bashrc
	source ~/.bashrc
	rm apache-maven-3.3.9-bin.tar.gz

Download oozie
	wget http://www.eu.apache.org/dist/oozie/4.2.0/oozie-4.2.0.tar.gz
	tar xvfz oozie-4.2.0.tar.gz
	mv oozie-4.2.0 ooziesrc
	rm oozie-4.2.0.tar.gz

Build Oozie
	cd ooziesrc
	./bin/mkdistro.sh -DskipTests 

	4.2 versions of oozie pom.xml have a bug that refers to codehaus.org repo which is not serving maven anymore, if you encounter a build failure comment the codehaus repository id as shown below in your oozie pom.xml
		  <!-- <repository>
	            <id>Codehaus repository</id>
	            <url>http://repository.codehaus.org/</url>
	            <snapshots>
	                <enabled>false</enabled>
	            </snapshots>
	        </repository> -->


	Also, if you are on JDK1.8 set the maven java docs option to bwelow, if not you would encounter a build error
		        <maven.javadoc.opts>-Xdoclint:none</maven.javadoc.opts>


Once the build is done copy the generated dist to your home and extract (ideally, we should use a separate user for oozie, but for now we continue with the same user that has built oozie)

cp distro/target/oozie-4.2.0-distro.tar.gz ~
cd ~
tar xvfz oozie-4.2.0-distro.tar.gz
mv oozie-4.2.0 oozie

cd oozie
wget http://dev.sencha.com/deploy/ext-2.2.zip
get hadoop version (hadoop version)

mkdir libext
cp $HADOOP_HOME/share/hadoop/common/lib/*.jar libext
cp ext-2.2.zip libext



-------------the data
Create a DIR (/tmp/cs) and change permissions to RW for all (recursive)
Upload the data to DFS (regusers.tsv, urlmap.tsv, omniture.tsv)

Create Users Table

	create table users (swid STRING, birth_dt STRING, gender_cd CHAR(1))
	ROW FORMAT DELIMITED 
	FIELDS TERMINATED by '\t' 
	stored as textfile tblproperties ("skip.header.line.count"="1");

Create Products Table
	create table products (url STRING, category STRING)
	ROW FORMAT DELIMITED 
	FIELDS TERMINATED by '\t'
	stored as textfile tblproperties ("skip.header.line.count"="1");

Create click stream table
	create table omniturelogs (col_1 STRING,col_2 STRING,col_3 STRING,col_4 STRING,col_5 STRING,col_6 STRING,col_7 STRING,col_8 STRING,col_9 STRING,col_10 STRING,col_11 STRING,col_12 STRING,col_13 STRING,col_14 STRING,col_15 STRING,col_16 STRING,col_17 STRING,col_18 STRING,col_19 STRING,col_20 STRING,col_21 STRING,col_22 STRING,col_23 STRING,col_24 STRING,col_25 STRING,col_26 STRING,col_27 STRING,col_28 STRING,col_29 STRING,col_30 STRING,col_31 STRING,col_32 STRING,col_33 STRING,col_34 STRING,col_35 STRING,col_36 STRING,col_37 STRING,col_38 STRING,col_39 STRING,col_40 STRING,col_41 STRING,col_42 STRING,col_43 STRING,col_44 STRING,col_45 STRING,col_46 STRING,col_47 STRING,col_48 STRING,col_49 STRING,col_50 STRING,col_51 STRING,col_52 STRING,col_53 STRING) 
	ROW FORMAT DELIMITED 
	FIELDS TERMINATED by '\t' 
	stored as textfile tblproperties ("skip.header.line.count"="1");

Load Data
 LOAD DATA INPATH '/tmp/cs/urlmap.tsv' OVERWRITE INTO TABLE products;
 LOAD DATA INPATH '/tmp/cs/regusers.tsv' OVERWRITE INTO TABLE users;
 LOAD DATA INPATH '/tmp/cs/omniture.tsv' OVERWRITE INTO TABLE omniturelogs;


project a view, since we dont need all columns from omniture
	
	CREATE VIEW omniture AS SELECT col_2 ts, col_8 ip, col_13 url, col_14 swid, col_50 city, col_51 country, col_53 state FROM omniturelogs


Create a Join to get analytics out
	create table csa as select to_date(o.ts) logdate, o.url, o.ip, o.city, upper(o.state) state, o.country, p.category, CAST(datediff( from_unixtime( unix_timestamp() ), from_unixtime( unix_timestamp(u.birth_dt, 'dd-MMM-yy'))) / 365 AS INT) age, u.gender_cd from 
	omniture o inner join products p 
	on o.url = p.url left outer join users u on o.swid = concat('{', u.swid , '}')

-------------create a table in mysql
CREATE TABLE `csanalytics` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`vist_date` VARCHAR(20) NULL,
	`url` VARCHAR(100) NULL,
	`ip` VARCHAR(100) NULL,
	`city`  VARCHAR(100) NULL,
	`state` VARCHAR(100) NULL,
	`country` VARCHAR(100) NULL,
	`category` VARCHAR(100) NULL,
	`age` VARCHAR(100) NULL,
	`gender` VARCHAR(100) NULL,
	PRIMARY KEY (`id`)
)






-----Here comes the Oozie requirement

Use Case-
  a file named /user/cloudera/a.tsv would come to HDFS every day, we need to load this into Hive and the do an export to MySQL

  Sqoop would be run from OOzie and that means we need the SQLDriver to be placed in share lib (on CHD upload the driver to / user/ oozie/ share/ lib/ sqoop)

Create a script as following and model the workflow.

loaddata.hql (Input to Hive)
	LOAD DATA INPATH '/tmp/cs/a.tsv' OVERWRITE INTO TABLE omniturelogs;

storedata.hql (Output from Hive to HDFS)

	CREATE EXTERNAL TABLE IF NOT EXISTS csaresults (
	logdate STRING, url STRING, ip STRING, scity STRING, sstate STRING, country STRING, category STRING, age STRING, gender STRING
	  )
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY ','
	LINES TERMINATED BY '\n'
	STORED AS TEXTFILE
	LOCATION  '/tmp/cs/stage';

	INSERT OVERWRITE table csaresults select * from csa where age is not null and gender_cd is not null;

---create the oozie xml (or) model it in Hue and Run
---Use excel for your analytics

<workflow-app name="My_Workflow" xmlns="uri:oozie:workflow:0.5">
    <start to="fs-b17d"/>
    <kill name="Kill">
        <message>Action failed, error message[${wf:errorMessage(wf:lastErrorNode())}]</message>
    </kill>
    <action name="fs-b17d">
        <fs>
              <move source='${nameNode}/user/cloudera/a.tsv' target='${nameNode}/tmp/cs'/>
        </fs>
        <ok to="hive2-5f98"/>
        <error to="Kill"/>
    </action>
    <action name="hive2-5f98" cred="hive2">
        <hive2 xmlns="uri:oozie:hive2-action:0.1">
            <job-tracker>${jobTracker}</job-tracker>
            <name-node>${nameNode}</name-node>
            <jdbc-url>jdbc:hive2://quickstart.cloudera:10000/default</jdbc-url>
            <script>/tmp/cs/loaddata.sql</script>
        </hive2>
        <ok to="fs-4254"/>
        <error to="Kill"/>
    </action>
    <action name="hive2-eafe" cred="hive2">
        <hive2 xmlns="uri:oozie:hive2-action:0.1">
            <job-tracker>${jobTracker}</job-tracker>
            <name-node>${nameNode}</name-node>
            <jdbc-url>jdbc:hive2://quickstart.cloudera:10000/default</jdbc-url>
            <script>/tmp/cs/storedata.sql</script>
        </hive2>
        <ok to="sqoop-0991"/>
        <error to="Kill"/>
    </action>
    <action name="sqoop-0991">
        <sqoop xmlns="uri:oozie:sqoop-action:0.2">
            <job-tracker>${jobTracker}</job-tracker>
            <name-node>${nameNode}</name-node>
            <command>export --connect jdbc:mysql://ixatlabmaster:3306/hdptests --username lab --password killer --table csanalytics  --columns &quot;vist_date,url,ip,city,state,country,category,age,gender&quot;  --export-dir /tmp/cs/stage -m 1
</command>
        </sqoop>
        <ok to="End"/>
        <error to="Kill"/>
    </action>
    <action name="fs-4254">
        <fs>
              <delete path='${nameNode}/tmp/cs/stage'/>
        </fs>
        <ok to="hive2-eafe"/>
        <error to="Kill"/>
    </action>
    <end name="End"/>
</workflow-app>