Demonstration of JobChaining using ChainMapper class
We convert the raw data to Lower Case using LowerCaseMapper and then eliminate the noise words using another mapper (NoiseWordFilterMapper), and finally use the famous wordcount mapper.
We could alternatively (and ideally) do a Noiseword filter first and reduce the amount of transformation in LowerCase mapper.

Also, we use Job Counters to get further insights into various phases.

The application uses ToolRunner

-- compile the app using maven

start-dfs.sh and import the raw data
start-yarn.sh
mr-jobhistory-daemon.sh  start historyserver

---run the job and examine the counter output and also examine the individual logs
yarn logs -applicationId <theAppID>