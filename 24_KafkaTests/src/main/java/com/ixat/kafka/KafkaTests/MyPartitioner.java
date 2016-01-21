package com.ixat.kafka.KafkaTests;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class MyPartitioner implements Partitioner {
	
	public MyPartitioner(VerifiableProperties prop){
		
	}
	private static int counter;
	public int partition(Object key, int numPartitions) {
		// TODO Auto-generated method stub
		return (counter++ % numPartitions);
		
	}

}