package com.ixat.ioformats.inputformats;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileAsBinaryOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class CombineFormatTest extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(),
				new CombineFormatTest(), args);
		System.exit(res);

	}


	public int run(String[] args)  throws Exception{
		
		if (args.length < 2) {
			System.err.println("Usage: CombineFormatTest <input_dir> <output_path> [sequence to write the content in SOF] [compress]");
			System.exit(-1);
		}
	
		String inputPath = args[0];
		String outputPath = args[1];
		Configuration config = getConf();
		Job job = Job.getInstance(config, "CombineFormatTest");
		job.setNumReduceTasks(0);
		job.setJarByClass(CombineFormatTest.class);
		job.setMapperClass(CombineMapper.class);
		
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);

			
		job.setInputFormatClass(CombineTextInputFormat.class);
		
		job.setOutputFormatClass(TextOutputFormat.class);
		
		if(args.length>2 && args[2].equalsIgnoreCase("sequence")){
			job.setOutputFormatClass(SequenceFileOutputFormat.class);
			
		}
		if(args.length>3 && args[3].equalsIgnoreCase("compress")){
			SequenceFileOutputFormat.setOutputCompressionType(job, CompressionType.BLOCK);
			SequenceFileOutputFormat.setOutputCompressorClass(job, BZip2Codec.class);
		}
		
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));

		int exitStatus = job.waitForCompletion(true) ? 0 : 1;
		
	
		return exitStatus;
	}

	public static class CombineMapper extends Mapper<LongWritable, Text, LongWritable, Text>
	{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			context.write(key, value); // or use nullwritable for the key
		}
	}
}
