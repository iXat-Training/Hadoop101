start-dfs.sh
start-yarn.sh
mr-jobhistory-daemon.sh  start historyserver

 yarn jar $HADOOP_HOME/share/hadoop/yarn/*distributedshell-*.jar org.apache.hadoop.yarn.applications.distributedshell.Client -jar $HADOOP_HOME/share/hadoop/yarn/*distributedshell-*.jar  -shell_command 'ls' -shell_args '-la'

examine the logs of distShell using - 

yarn logs -applicationId <theAppID>