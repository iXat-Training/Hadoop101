tar xvzf apache-flume-1.6.0-bin.tar.gz
mv apache-flume-1.6.0-bin flume
~/.bashrc


Monitor /home/hduser/flumetests send the content of any new file to a log sink (1 src, 1 sink)

In flume1.conf
	myagent.sources = filesrc
	myagent.sinks = logsink 
	myagent.channels = c1

	# Describe/configure the source
	myagent.sources.filesrc.type = spooldir
	myagent.sources.filesrc.spoolDir = /home/hduser/flumetests

	# Describe the sink
	myagent.sinks.logsink.type = logger



	# Use a channel which buffers events in file
	myagent.channels.c1.type = file


	# Bind the source and sink to the channel
	myagent.sources.filesrc.channels = c1
	myagent.sinks.logsink.channel = c1


Run the agent
	flume-ng agent -f flume1.conf -n myagent -Dflume.root.logger=INFO,console


Create one more file for HDFS + Logger sink (1 Src, 2 Sinks)
	   	myagent.sources = dirsrc
        myagent.sinks = logsink hdfssink
        myagent.channels = c1 c2

        # Describe/configure the source
        myagent.sources.dirsrc.type = spooldir
        myagent.sources.dirsrc.spoolDir = /home/hduser/flumetests

        # Describe the sink
        myagent.sinks.logsink.type = logger

        myagent.sinks.hdfssink.type = hdfs
        myagent.sinks.hdfssink.hdfs.path = /flumecreated
        myagent.sinks.hdfssink.hdfs.fileType = DataStream


        # Use a channel which buffers events in file
        myagent.channels.c1.type = file
        myagent.channels.c1.checkpointDir = ./.flume/file-channel1/checkpoint
        myagent.channels.c1.dataDirs = ./.flume/file-channel1/data 

        myagent.channels.c2.type = file
        myagent.channels.c2.checkpointDir = ./.flume/file-channel2/checkpoint ##without this there would be a lock issue with channel1
        myagent.channels.c2.dataDirs = ./.flume/file-channel2/data ##without this there would be a lock issue with channel1


        # Bind the source and sink to the channel
        myagent.sources.dirsrc.channels = c1 c2
        myagent.sinks.logsink.channel = c1
        myagent.sinks.hdfssink.channel = c2


 flume-ng agent -f flume2.conf -n myagent -Dflume.root.logger=INFO,console


In one shell do the following, this would create two Files named FILE1 and FILE2 with some content with random sleep's, we would be reading them via FLUME and send the file content to another file (two sources, 1 sink)
	create a directory flumetests
		mkdir -p flumetests

	run the below command (ctrl + c to stop)
	while true; do   echo "File 1 `date`" >> FILE1;     echo "File 2 `date`" >> FILE2;  sleep `shuf -i 1-5 -n 1`;done

In another window, create flume3.conf with the below content

myagent.sources = source1 source2
myagent.sinks = sink1
myagent.channels = channel1

myagent.sources.source1.type = exec
myagent.sources.source1.command = tail -F FILE1
myagent.sources.source1.channels = channel1

myagent.sources.source2.type = exec
myagent.sources.source2.command = tail -F FILE2
myagent.sources.source2.channels = channel1

myagent.channels.channel1.type = memory

myagent.sinks.sink1.type = file_roll
myagent.sinks.sink1.batchSize = 2
myagent.sinks.sink1.channel = channel1
myagent.sinks.sink1.sink.directory = ./flumetests
myagent.sinks.sink1.sink.rollInterval = 30
myagent.sinks.sink1.sink.serializer = TEXT


run the agent
	flume-ng agent -f flume3.conf -n myagent -Dflume.root.logger=INFO,console

Examine the file under flumetests dir, we are simulating reading a webserver log file and mashing them into one log sink

Lets add an HDFS sync for the above (in flume4.conf)
myagent.sources = source1 source2
myagent.sinks = sink1 sink2
myagent.channels = channel1 channel2

myagent.sources.source1.type = exec
myagent.sources.source1.command = tail -F FILE1
myagent.sources.source1.channels = channel1 channel2

myagent.sources.source2.type = exec
myagent.sources.source2.command = tail -F FILE2
myagent.sources.source2.channels = channel1 channel2

myagent.channels.channel1.type = memory
myagent.channels.channel2.type = memory

myagent.sinks.sink1.type = file_roll
myagent.sinks.sink1.batchSize = 2
myagent.sinks.sink1.channel = channel1
myagent.sinks.sink1.sink.directory = ./flumetests
myagent.sinks.sink1.sink.rollInterval = 3600
myagent.sinks.sink1.sink.serializer = TEXT

myagent.sinks.sink2.type = hdfs
myagent.sinks.sink2.hdfs.path = /flumecreated/h1
myagent.sinks.sink2.hdfs.fileType = DataStream
myagent.sinks.sink2.channel = channel2

run the agent
	flume-ng agent -f flume4.conf -n myagent -Dflume.root.logger=INFO,console

Examine the HDFS content
