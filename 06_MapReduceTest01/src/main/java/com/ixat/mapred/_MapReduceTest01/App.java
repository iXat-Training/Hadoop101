package com.ixat.mapred._MapReduceTest01;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	System.setProperty("HADOOP_USER_NAME", "hduser");
    	
    	try {
			Configuration conf = new Configuration();
			conf.set("mapreduce.app-submission.cross-platform", "true");
			conf.set("mapred.remote.os", "Linux");
			conf.set("mapreduce.remote.os", "Linux");
			
			Job job = new Job(conf, "wordcount");
			
			job.setJarByClass(App.class);
			
			job.setMapperClass(MyMapper.class);
			job.setReducerClass(MyReducer.class);

			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputFormatClass(TextOutputFormat.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);

		
			
			String outDir = "/test/RESULTS_" + System.currentTimeMillis();
			
			//We have created the a.txt in /test folder of HDFS
			FileInputFormat.addInputPath(job, new Path("/test/a.txt"));
			FileOutputFormat.setOutputPath(job, new Path( outDir));

			job.waitForCompletion(true);
			System.out.println("Job Completed, results are stored in the directory - " + outDir);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
