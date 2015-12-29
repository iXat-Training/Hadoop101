package com.ixat.job.chaining.mappers;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class LowerCaseMapper extends
Mapper<LongWritable, Text,  LongWritable,Text>{
	
Text record = new Text();
	
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		record.set(value.toString().toLowerCase());
		context.write(key, record);
		context.getCounter(com.ixat.job.chaining.counters.TRANSFORMATIONCOUNTERS.LOWERCASE_TRANSFORMATION).increment(1);
	
	}


}
