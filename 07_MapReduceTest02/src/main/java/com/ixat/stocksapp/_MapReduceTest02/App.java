package com.ixat.stocksapp._MapReduceTest02;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		
		System.setProperty("HADOOP_USER_NAME", "hduser");
    	
    	try {
			Configuration conf = new Configuration();
			conf.set("mapreduce.app-submission.cross-platform", "true");
			conf.set("mapred.remote.os", "Linux");
			conf.set("mapreduce.remote.os", "Linux");
			
			Job job = new Job(conf, "StockTradeAnalyzer");
			
			job.setJarByClass(App.class);
			
			job.setMapperClass(StockVolumeMapper.class);
			job.setReducerClass(StockVolumeReducer.class);

		
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(LongWritable.class);

		
			
			String outDir = "/stocks/STOCKRESULTS_" + System.currentTimeMillis();
			
			//We have created the a.txt in /test folder of HDFS
			FileInputFormat.addInputPath(job, new Path("/stocks/stocks_2014-2015.csv"));
			
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

	public static class StockVolumeMapper extends
			Mapper<LongWritable, Text, Text, LongWritable> {

		Text symbol = new Text();
		LongWritable tradedVolumePerDay = new LongWritable();

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] parts = line.split(",");
			String stockSymbol = parts[0];
			long tradedVolume = Long.parseLong(parts[6]);

			symbol.set(stockSymbol);
			tradedVolumePerDay.set(tradedVolume);
			context.write(symbol, tradedVolumePerDay);

		}
	}

	public static class StockVolumeReducer extends
			Reducer<Text, LongWritable, Text, LongWritable> {

		public void reduce(Text key, Iterable<LongWritable> values,
				Context context) throws IOException, InterruptedException {
			long sum = 0;
			for (LongWritable val : values) {
				sum += val.get();
			}
			context.write(key, new LongWritable(sum));
		}
	}

}
