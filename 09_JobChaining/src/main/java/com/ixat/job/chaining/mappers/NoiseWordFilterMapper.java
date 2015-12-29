package com.ixat.job.chaining.mappers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class NoiseWordFilterMapper extends
Mapper<LongWritable, Text,  LongWritable,Text>{

	private static String[] noisewords = new String[]{ "the", "and", "a", "to", "of", "in", "i", "is", "that", "it", "on", "you", "this", "for", "but", "with", "are", "have", "be", "at", "or", "as", "was", "so", "if", "out", "not"};

	List<String> noisewordsList = null;
	
	public void setup(Context ctx){
		noisewordsList = Arrays.asList(noisewords);

		
	}
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
	
		StringTokenizer st = new StringTokenizer(value.toString());
		StringBuffer buffer = new StringBuffer();
		while(st.hasMoreTokens()){
			String word = st.nextToken();
			if(!noisewordsList.contains(word)){
				if(buffer.length()>0)
					buffer.append(" ");
				buffer.append(word);
			}else{
				context.getCounter(com.ixat.job.chaining.counters.TRANSFORMATIONCOUNTERS.NWRECORD_ELIMINATIONS).increment(1);
				
			}
		}
		context.write(key, new Text(buffer.toString()));
		
	}

}
