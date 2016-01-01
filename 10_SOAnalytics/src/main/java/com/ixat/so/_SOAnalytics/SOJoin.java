package com.ixat.so._SOAnalytics;

import java.io.IOException;
import java.util.ArrayList;

import nanoxml.XMLElement;
import nanoxml.XMLParseException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class SOJoin extends Configured implements Tool {

		public static void main(String[] args) throws Exception {
			// Let ToolRunner handle generic command-line options
			int res = ToolRunner.run(new SOJoin(), args);

			System.exit(res);
		}

		public int run(String[] args) throws Exception {
			Configuration conf = getConf();

			Job job = Job
					.getInstance(conf,
							"Stackoverflow Joins");
			if (args.length < 2) {
				System.out.println("usage SOJoin <infile1-users> <infile2-comments> <outdir>");
				return -100;
			}
			
			Path users = new Path(args[0]);
			Path comments = new Path(args[1]);
			Path out = new Path(args[2]);

			FileOutputFormat.setOutputPath(job, out);

			MultipleInputs.addInputPath(job, users,
					TextInputFormat.class, SOUserMapper.class);

			MultipleInputs.addInputPath(job, comments,
					TextInputFormat.class, SOCommentsMapper.class);
			
			job.setJarByClass(SOJoin.class);
			
			job.setReducerClass(SOJoinReducer.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			
			
			if (job.waitForCompletion(true)) {
				return 0;
			} else {
				return -200;
			}

		}
		
		
		public static class SOUserMapper extends 	Mapper<LongWritable, Text,  Text,Text>{
			
			private Text outKey = new Text();
			private Text outValue = new Text();
			
			public void map(LongWritable key, Text value, Context context)
					throws IOException, InterruptedException {
				String sxml = value.toString();
				
				try {
					XMLElement element = new XMLElement();
					element.parseString(sxml);
					
					String UserId = element.getAttribute("Id").toString();
					
					if(UserId==null ) return;
					
					outKey.set(UserId);
					outValue.set("USER:" + value);
					context.write(outKey, outValue);
					
				} catch (XMLParseException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
			}
		}
		
		
	public static class SOCommentsMapper extends 	Mapper<LongWritable, Text,  Text,Text>{
			
			private Text outKey = new Text();
			private Text outValue = new Text();
			
			public void map(LongWritable key, Text value, Context context)
					throws IOException, InterruptedException {
				String sxml = value.toString();
				
				try {
					XMLElement element = new XMLElement();
					element.parseString(sxml);
					
					if(element.getAttribute("UserId")==null) return;
					
					String UserId = element.getAttribute("UserId").toString();
					
					if(UserId==null ) return;
					
					outKey.set(UserId);
					outValue.set("COMM:" + value);
					context.write(outKey, outValue);
					
				} catch (XMLParseException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
			}
	}
	
	public static class SOJoinReducer extends Reducer<Text, Text, Text, Text> {

		private ArrayList<Text> listA = new ArrayList<Text>();
		private ArrayList<Text> listB = new ArrayList<Text>();
		
		private Text outKey = new Text();
		private Text outVal = new Text();
		
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			// Clear our lists
			listA.clear();
			listB.clear();

			for (Text t : values) {
				if (t.find("USER:") == 0) {
					listA.add(new Text(t.toString().substring(5)));
				} else if (t.find("COMM:") == 0) {
					listB.add(new Text(t.toString().substring(5)));
				}
			}
			
			if (!listA.isEmpty() && !listB.isEmpty()) {
				for (Text A : listA) {
					for (Text B : listB) {
						XMLElement userelement = new XMLElement();
						userelement.parseString(A.toString());
						XMLElement commentelement = new XMLElement();
						commentelement.parseString(B.toString());
						
						String UserId = userelement.getAttribute("Id").toString();
						String UserName = userelement.getAttribute("DisplayName").toString();
						String comment = commentelement.getAttribute("Text").toString();
						outKey.set(UserId + ":" + UserName);
						outVal.set(comment);
						context.write(outKey, outVal);
					}
				}
			}		
		}
	}
		
}
