package com.ixat.ioformats.custom;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;



public class EmpDriver {
	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			String[] arg = new GenericOptionsParser(conf, args).getRemainingArgs();
			Job job = Job.getInstance(conf, "Employee Custom Processing");
			job.setJarByClass(EmpDriver.class);
			job.setMapperClass(MyMapper.class);

			job.setNumReduceTasks(0);

			job.setInputFormatClass(EmployeeInputFormat.class);

			job.setMapOutputKeyClass(LongWritable.class);
			job.setMapOutputValueClass(EmployeeWritable.class);

			job.setOutputKeyClass(LongWritable.class);
			job.setOutputValueClass(EmployeeWritable.class);

			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));

			job.waitForCompletion(true);
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
	
	public static class MyMapper extends Mapper<LongWritable, EmployeeWritable, LongWritable, EmployeeWritable> {
		
		public void map(LongWritable key, EmployeeWritable value, Context context) throws IOException, InterruptedException {
			context.write(key,  value);
			
		}
		
	}
}
