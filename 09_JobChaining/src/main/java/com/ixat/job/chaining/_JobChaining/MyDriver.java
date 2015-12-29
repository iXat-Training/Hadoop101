package com.ixat.job.chaining._JobChaining;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.ixat.job.chaining.counters.TRANSFORMATIONCOUNTERS;
import com.ixat.job.chaining.mappers.LowerCaseMapper;
import com.ixat.job.chaining.mappers.NoiseWordFilterMapper;
import com.ixat.job.chaining.mappers.WordCountMapper;
import com.ixat.job.chaining.reducers.WordCountReducer;

public class MyDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		// Let ToolRunner handle generic command-line options
		int res = ToolRunner.run(new MyDriver(), args);

		System.exit(res);
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();

		Job job = Job
				.getInstance(conf,
						"Chaining jobs, eliminate noise words, convert to lower and count the words");
		if (args.length < 2) {
			System.out.println("usage JobName <infile> <outdir>");
			return -100;
		}
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		FileInputFormat.addInputPath(job, in);
		FileOutputFormat.setOutputPath(job, out);


		job.setJarByClass(MyDriver.class);
		
		
		ChainMapper.addMapper(job, LowerCaseMapper.class, LongWritable.class, Text.class, LongWritable.class, Text.class, null);
		ChainMapper.addMapper(job, NoiseWordFilterMapper.class, LongWritable.class, Text.class, LongWritable.class, Text.class, null);
		ChainMapper.addMapper(job, WordCountMapper.class, LongWritable.class, Text.class, Text.class,IntWritable.class, null);
		
		job.setReducerClass(WordCountReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		if (job.waitForCompletion(true)) {
			
			System.out.println("Total #of NW eliminations =" + job.getCounters().findCounter(TRANSFORMATIONCOUNTERS.NWRECORD_ELIMINATIONS).getValue());
			
			return 0;
		} else {
			return -200;
		}

	}

}
