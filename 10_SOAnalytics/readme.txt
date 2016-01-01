Deriving an InvertedIndex on StackOverflow posts and Joins...

SOWikiAnalyzer sample which demonstrates the following
  - Inverted Index on Stackoverflow feed (you can get the feed content from https://archive.org/download/stackexchange)
  
  - A small set is included in data folder of the project
  
  - Inclusion of a custom jar in the job, maven xml has been modified to package the depency jar in the lib folder of target jar
  
  - A custom partitioner to distribute the rows on user choosen scheme
  
  - A combiner for optimization


SOJoin - One more sample to demonstrate ReduceSide Inner Join - 

 

Compile the above using maven
  Load Posts.xml from data dir into HDFS
  Run using yarn jar <jarFileName> package.SOWikiAnalyzer <hdfsInput> <outputFolder> [optionally the #of reducers] [optionally true if you want a combiner]

  examine the results with multiple partitions
  examine the counters with and without the combiner
  examine multiple mappers in action by loading the file with a lesser block size
      ## remove the posts.xml
      $hdfs dfs -rm /so/posts.xml

      ## load the posts.xml again with a 1MB block size
      $hdfs dfs -D dfs.block.size="1048576" -put Posts.xml  /so/posts.xml

      ##you can check the blocks of a given file using the below
      $hdfs fsck  /so/posts.xml -blocks
    
    finally run the SOWikiAnalyzer to see two mappers in action.
  
