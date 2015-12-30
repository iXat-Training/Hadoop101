Sample to demonstrate the following
  - Inverted Index on Stackoverflow feed (you can get the feed content from https://archive.org/download/stackexchange)
  		- A small set is included in data folder of the project
  - Inclusion of a custom jar in the job, maven xml has been modified to package the depency jar in the lib folder of target jar
  - A custom partitioner to distribute the rows on user choosen scheme

  Compile using maven
  Load Posts.xml from data dir into HDFS
  Run using yarn jar <jarFileName> <hdfsInput> <outputFolder> [optionally the #of reducers]
  
  TODO: 
  	Refine the partitioner
  	Implement a combiner
