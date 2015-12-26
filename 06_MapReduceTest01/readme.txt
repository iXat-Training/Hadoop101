================MapReduce applications=============

We would be using yarn, create the following configurations

Create a mapred config file ($HADOOP_HOME/etc/hadoop/mapred-site.xml) with below content

		<configuration>
		 <property>
		  <name>mapreduce.framework.name</name>
		   <value>yarn</value>  <!-- or set this to local for debug needs -->
		 </property>
		</configuration>


Create a yarn config file ($HADOOP_HOME/etc/hadoop/yarn-site.xml) with below content

<configuration>
        <property>
         <name>yarn.nodemanager.delete.debug-delay-sec</name>
         <value>600</value>
         </property>
        <property>
            <name>yarn.resourcemanager.hostname</name>
            <value>localhost</value>
        </property>
        <property>
            <name>yarn.nodemanager.aux-services</name>
            <value>mapreduce_shuffle</value>
        </property>
        <property>
            <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
            <value>org.apache.hadoop.mapred.ShuffleHandler</value>
        </property>
        <property>
               <name>yarn.application.classpath</name>
               <value>
                    $HADOOP_CONF_DIR,
                    $HADOOP_COMMON_HOME/share/hadoop/common/*,
                    $HADOOP_COMMON_HOME/share/hadoop/common/lib/*,
                    $HADOOP_HDFS_HOME/share/hadoop/hdfs/*,
                    $HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,
                    $YARN_HOME/share/hadoop/yarn/*,
                    $YARN_HOME/share/hadoop/yarn/lib/*,
                    $YARN_HOME/share/hadoop/mapreduce/*,
                    $YARN_HOME/share/hadoop/mapreduce/lib/*
               </value>
        </property>
        <property>
            <name>yarn.resourcemanager.bind-host</name>
            <value>0.0.0.0</value>
        </property>
        <property>
            <name>yarn.nodemanager.bind-host</name>
            <value>0.0.0.0</value>
        </property>
        <property>
            <name>yarn.timeline-service.bind-host</name>
            <value>0.0.0.0</value>
        </property>
        <property>
      <name>yarn.log-aggregation-enable</name>
      <value>true</value>
  </property>
  <property>
     <name>yarn.nodemanager.remote-app-log-dir</name>
     <value>/app-logs</value>
  </property>
  <property>
      <name>yarn.nodemanager.remote-app-log-dir-suffix</name>
      <value>logs</value>
  </property>
  <property>
  <name>yarn.log.server.url</name>
  <value>http://localhost:8188/jobhistory/logs</value>
</property>
</configuration>


set JAVA_HOME in $HADOOP_HOME/etc/hadoop/yarn-env.sh

start yarn using the command start-yarn.sh

verify if resource manager and nodemanager have indeed started using jps

map the following ports of the CentOS Guest from the VirtualBox	to Host O/S and open http://localhost:8088 in a browser to get info on the cluster

ResourceManager - 8032, 8033, 8088
NodeManager - 8042, 
MR Job History server - 10020, 19888

On your windows host edit C:\Windows\System32\drivers\etc\hosts and add the VM name with the IP 127.0.0.1
On your VM (CentOS) edit /etc/hosts and add the VM name with the IP 127.0.0.1



======================Running the WC Program in Yarn
1. Ensure there are no errors in the source
2. export the compiled project as a jar
3. Run the app using the following command
		yarn jar jarfileName theAbsoluteClassName

    for example
        yarn jar myjob1.jar com.ixat.mapred._MapReduceTest01.App

             (or)


        java -classpath /home/hduser/hadoop/etc/hadoop:/home/hduser/hadoop/share/hadoop/common/lib/*:/home/hduser/hadoop/share/hadoop/common/*:/home/hduser/hadoop/share/hadoop/hdfs:/home/hduser/hadoop/share/hadoop/hdfs/lib/*:/home/hduser/hadoop/share/hadoop/hdfs/*:/home/hduser/hadoop/share/hadoop/yarn/lib/*:/home/hduser/hadoop/share/hadoop/yarn/*:/home/hduser/hadoop/share/hadoop/mapreduce/lib/*:/home/hduser/hadoop/share/hadoop/mapreduce/*:/home/hduser/hadoop/contrib/capacity-scheduler/*.jar:/home/hduser/* com.ixat.mapred._MapReduceTest01.App

