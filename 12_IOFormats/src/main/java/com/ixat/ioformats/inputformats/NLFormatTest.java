package com.ixat.ioformats.inputformats;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class NLFormatTest extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(),
				new NLFormatTest(), args);
		System.exit(res);

	}


	public int run(String[] args)  throws Exception{
		
		if (args.length < 2) {
			System.err.println("Usage: NLFormatTest <input_path> <output_path> [numReducers] [useCombiner --> true|false] [PartitionOnUserID --> true | false]");
			System.exit(-1);
		}
	
		String inputPath = args[0];
		String outputPath = args[1];
		Configuration config = getConf();
		Job job = Job.getInstance(config, "NLTests");
		job.setNumReduceTasks(0);
		job.setJarByClass(NLFormatTest.class);
		job.setMapperClass(NLMapper.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);

		job.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap", 100);
		
		job.setInputFormatClass(NLineInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		NLineInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));

		int exitStatus = job.waitForCompletion(true) ? 0 : 1;
		
	
		return exitStatus;
	}

	public static class NLMapper extends Mapper<LongWritable, Text, LongWritable, Text>
	{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			context.write(key, value);
		}
	}
}
