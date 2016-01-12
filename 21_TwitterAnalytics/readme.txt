TwitterAnalytics - A Mini project for the long weekend.
	
Objective - Sentiment analysis on Tweets posted on Twitter using Hadoop(HDFS+MR+PIG+HIVE+DRILL+FLUME).

Requirements -
	Write a Flume Twitter source to ingest real time tweets given a keyword criteria
	Store the Twitter feeds partitioned by Date and Hour in HDFS, a sample Flume Twitter Source is provided for your reference. 
	Twitter content is usually an OR of keywords provided in the FilterQuery, derive a logical AND of tweets on the provided keywords
	Twitter content over a period of time can result in multiple small files in HDFS, All historical Twitter (Current Day - 1) feeds need to be consolidated a bigger files (of size 64MB) and stored in HDFS.
	Do a sentiment analysis of Tweets using Stanford NLP (example provided)
	Generate a Timeseries graph (or a scatter) in Tableu on the sentiment score and with count of tweets.
	Write a utility which would take hour of the day as input and displays all the possible sentiment scores and #count of tweets for that hour. When given a sentiment score and hour, the utility should fetch all originall tweets which fall in this criteria.

Generate your own OAUTH tokens (the access keys) to access twitter over here - https://apps.twitter.com/, i have masked mine in the source.


Technologies to use
	Twitter4j
	Flume
	HDFS
	Map Reduce on YARN
	PIG
	HIVE
	DRILL
	StanfordNLP
	Tableu




