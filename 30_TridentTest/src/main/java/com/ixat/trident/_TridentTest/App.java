package com.ixat.trident._TridentTest;

import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.Debug;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.Split;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

    	FixedBatchSpout fbspout = new FixedBatchSpout(new Fields("line"), 3, new Values("the cow jumped over the moon"),
    	        new Values("the man went to the store and bought some candy"), new Values("four score and seven years ago"),
    	        new Values("how many apples can you eat"), new Values("to be or not to be the person"));
    	fbspout.setCycle(false);
        
    	
    	
    	 TridentTopology topology = new TridentTopology();
    	 topology.newStream("spout",fbspout)
         .each(new Fields("line"), new Split(), new Fields("word"))
         .groupBy(new Fields("word"))
         .aggregate(new Fields("word"), new Count(), new Fields("count"))
         .each(new Fields("word","count"), new Debug());  
    	
    	 Config config = new Config();
         
    	 LocalCluster localCluster = new LocalCluster();

    	 localCluster.submitTopology("WordCount",config,topology.build());
    }
    
    
    
}
