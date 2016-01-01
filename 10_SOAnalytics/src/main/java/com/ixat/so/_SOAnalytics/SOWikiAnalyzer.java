package com.ixat.so._SOAnalytics;

import java.io.IOException;

import nanoxml.XMLElement;
import nanoxml.XMLParseException;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SOWikiAnalyzer extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		// Let ToolRunner handle generic command-line options
		int res = ToolRunner.run(new SOWikiAnalyzer(), args);

		System.exit(res);
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();

		Job job = Job
				.getInstance(conf,
						"Stackoverflow Analytics");
		if (args.length < 2) {
			System.out.println("usage SOWikiAnalyzer <infile> <outdir> [NumerOfReducers] [UserCombiner=true|false]");
			return -100;
		}
		
		int numReducers = 1;
		if(args.length>2)
			numReducers = Integer.parseInt(args[2]);
		
		Path in = new Path(args[0]);
		Path out = new Path(args[1]);
		FileInputFormat.addInputPath(job, in);
		FileOutputFormat.setOutputPath(job, out);


		job.setJarByClass(SOWikiAnalyzer.class);
		
		
		job.setMapperClass(WikiLinkMapper.class);
		job.setReducerClass(WikiLinkReducer.class);
		
		if(args.length>3 && args[3].toLowerCase().equals("true"))
			job.setCombinerClass(WikiLinkReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setNumReduceTasks(numReducers);
		
		job.setPartitionerClass(UserIDPartitioner.class);
		
		
		if (job.waitForCompletion(true)) {
			return 0;
		} else {
			return -200;
		}

	}
	
	
	
	public static class WikiLinkMapper extends
	Mapper<LongWritable, Text,  Text,Text>{
		
	Text tlink = new Text();
	Text tid = new Text();
		
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String sxml = value.toString();
			
			try {
				XMLElement element = new XMLElement();
				element.parseString(sxml);
				
				String Id = element.getAttribute("Id").toString();
				String PostTypeId = element.getAttribute("PostTypeId").toString();
				String Body = element.getAttribute("Body").toString();
				String UserID = "0";
				if(element.getAttribute("OwnerUserId")!=null){
					UserID = element.getAttribute("OwnerUserId").toString();
					//Ideally
					// return;
				
				}
				
				if(Id!=null &&
						PostTypeId!=null &&
						Body != null &&
						!Id.equals("") && 
						!PostTypeId.equals("") &&
						!PostTypeId.equals("1") &&
						!Body.equals("") &&
						Body.indexOf("http://en.wikipedia.org/wiki")>-1){
					
					int startIndex = Body.indexOf("\"http://en.wikipedia.org/wiki");
					int endIndex = Body.indexOf("\"", startIndex+1);
					String wikiLink = Body.substring(startIndex+1, endIndex);
					
					tid.set(Id+ ":" + UserID);	
					tlink.set(wikiLink );
					context.write(tlink,  tid);
					
				}
				
			} catch (XMLParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
		}
	}
	
	public static class WikiLinkReducer extends Reducer<Text, Text, Text, Text> {

	    public void reduce(Text key, Iterable<Text> values, Context context)
	       throws IOException, InterruptedException {
	     
	    	  StringBuffer sb = new StringBuffer();
	    	  
	          for (Text val : values) {
	        	  sb.append(val.toString());
	        	  sb.append(",");
	          }
	          context.write(key, new Text(sb.toString()));
	      
	    }
	}
	
	public static class UserIDPartitioner extends Partitioner<Text,Text> 
implements Configurable{
		
		private Configuration config;
		public int getPartition(Text key, Text value, int numReducers) {
			
			String[] parts = value.toString().split("\\:");
			int userId = Integer.parseInt(parts[1]);
			return userId % numReducers;
			
		}

		public void setConf(Configuration conf) {
			// TODO Auto-generated method stub
			config = conf;
		}

		public Configuration getConf() {
			// TODO Auto-generated method stub
			return config;
		}

		
		
	}
	
}
