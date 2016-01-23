export HBASE_HOME=/home/hduser/hbase
export PATH=$PATH:$HBASE_HOME/bin

To run in local mode modify the hbase/conf/hbase-site.xml to the below

	<property>
        <name>hbase.rootdir</name>
        <value>file:/home/hduser/hbase/data</value>
        </property>

        <property>
        <name>hbase.cluster.distributed</name>
        <value>false</value>
        </property>

        <property>
        <name>hbase.zookeeper.property.dataDir</name>
        <value>/home/hduser/hbase/zookeeper</value>
        </property>


Start hbase - $HBASE_HOME/bin/start-hbase.sh

Start the client -  hbase shell

>list 

>table_help

> create 'emp','identifier','compensation','address'

> describe 'emp'

> put 'emp','1111','identifier:empno','e1'
> put 'emp','1111','identifier:ename','JAMES'
> put 'emp','1111','compensation:salary','3000'
> put 'emp','1111','compensation:pf','30'
> put 'emp','1111','address:city','HYD'


> scan 'emp'

put 'emp','1112','identifier:empno','e2'
put 'emp','1112','identifier:ename','SMITH'
put 'emp','1112','compensation:salary','5000'
put 'emp','1112','compensation:pf','30'
put 'emp','1112','address:city','BLR'

put 'emp','1113','identifier:empno','e2'
put 'emp','1113','identifier:ename','SMITH'
put 'emp','1113','compensation:salary','5000'
put 'emp','1113','compensation:pf','30'
put 'emp','1113','address:city','HYD'
put 'emp','1113','address:street','KOTI'
put 'emp','1113','address:doorno','2333-333'

put 'emp','11110','identifier:empno','e3'
put 'emp','11110','identifier:ename','CLARK'
put 'emp','11110','compensation:salary','5000'
put 'emp','11110','compensation:pf','30'

>get 'emp','1111'

>delete 'emp','11110','identifier'

>disable 'emp'

> drop 'emp'


stop-hbase.sh

Modify the hbase-site.xml to the below for pseudo-dist mode, ensure you have zookeeper running.

<configuration>
        <property>
        <name>hbase.rootdir</name>
        <value>hdfs:/hbase/data</value>
        </property>

        <property>
        <name>hbase.cluster.distributed</name>
        <value>true</value>
        </property>



        <property>
        <name>hbase.zookeeper.quorum</name>
        <value>localhost</value>
        </property>

        <property>
        <name>dfs.replication</name>
        <value>1</value>
        </property>

        <property>
        <name>hbase.zookeeper.property.clientPort</name>
        <value>2181</value>
        </property>

</configuration>


--start zookeeper


java -cp hb.jar:`hbase classpath` com.ixat.hbase.HBaseTests.CreateTable

