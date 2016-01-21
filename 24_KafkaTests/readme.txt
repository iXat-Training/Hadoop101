Kafka is a distributed, partitioned, replicated commit log service. It provides the functionality of a messaging system.
	Kafka maintains feeds of messages in categories called topics.
	A processes that publish messages to a Kafka topic producers.
	A processes that subscribe to topics and process the feed of published messages consumers.
	Kafka is run as a cluster comprised of one or more servers each of which is called a broker.

So, at a high level, producers send messages over the network to the Kafka cluster which in turn serves them up to consumers

Communication between the clients and the servers is done with a simple, high-performance, language agnostic TCP protocol

A topic is a category or feed name to which messages are published. For each topic, the Kafka cluster maintains a partitioned log.

Each partition is an ordered, immutable sequence of messages that is continually appended to—a commit log. The messages in the partitions are each assigned a sequential id number called the offset that uniquely identifies each message within the partition.

Download Kafka from - https://www.apache.org/dyn/closer.cgi?path=/kafka/0.9.0.0/kafka_2.11-0.9.0.0.tgz

Unzip it to a folder

You get 
       --bin
	  |
	  |
	  -windows
       --libs
       --config
	  -server.properties

cd to bin if you are on Linux or to windows if on windows, or set the path appropriately

start zookeeper (lets say its on localhost:2128)

start kafka broker with appropriate config file, for example, modify config\server.properties and set the following keys, set the log.dirs to appropriate directory (create this path if this is not available)
	broker.id=0
	listeners=PLAINTEXT://:9092
	log.dirs=C:/Temp/kafka-logs
 		
start the server (use .sh or .bat according to your OS)
	kafka-server-start.bat <path to configDir>\server.properties

Create a topic named MyTopic1
	kafka-topics.bat  --create --topic MyTopic1 --zookeeper localhost:2128 --partitions 2 --replication-factor 1

Get properties of the topic just created
	kafka-topics.bat --describe --zookeeper localhost:2181 --topic MyTopic1

Start a console producer to send messages to MyTopic1
	kafka-console-producer.bat --broker-list "localhost:9092" --topic MyTopic1

In another window start a console consumer to receive messages from MyTopic1
	kafka-console-consumer.bat --topic MyTopic1 --zookeeper localhost:2181


Create a set of Brokers, you can choose to do this on multiple servers, but if done on same host for testing needs, ensure you change the log directory and listen port, for example
	copy the contents of server.property to the below 3 files and change the mentioned keys.
		s1.properties
			broker.id=0
			listeners=PLAINTEXT://:9092
			log.dirs=C:/Temp/kafka-logs/0
		
		s2.properties
			broker.id=1
			listeners=PLAINTEXT://:9093
			log.dirs=C:/Temp/kafka-logs/1
		
		s3.properties
			broker.id=2
			listeners=PLAINTEXT://:9094
			log.dirs=C:/Temp/kafka-logs/2

         Create the directories C:/Temp/kafka-logs/0, C:/Temp/kafka-logs/ and C:/Temp/kafka-logs/2

start 3 brokers (ensure zookeeper is up and running)
	start kafka-server-start.bat <path to config files>\s1.properties
	start kafka-server-start.bat <path to config files>\s2.properties
	start kafka-server-start.bat <path to config files>\s3.properties

Create a new topic
	kafka-topics.bat  --create --topic A1 --zookeeper --partitions 2 --replication-factor 2

Examine the created topic
	kafka-topics.bat --describe --zookeeper localhost:2181 --topic A1

Start a producer for the topic
	kafka-console-producer.bat --broker-list "localhost:9092,localhost:9093,localhost:9095" --topic A1


Start a set of consumers for the topic
	start kafka-console-consumer.bat --topic A1 --zookeeper localhost:2181
	start kafka-console-consumer.bat --topic A1 --zookeeper localhost:2181
	
Send a set of messages in the producer and examine how they arrive at the consumer

Now, create a properties file (to specify the groupid for the consumer)
	group.properties
		group.id=g1

kill the earlier consumers and restart them now with group configuration and observe the message delivery pattern
	start kafka-console-consumer.bat --topic A1 --zookeeper localhost:2181 	--consumer.config group.properties
	start kafka-console-consumer.bat --topic A1 --zookeeper localhost:2181 	--consumer.config group.properties

Now stop the producer and run the .NET producer
Experiment with the Java producer and consumers.
Try changing the partitions, #of clients etc...





