package com.ixat.hbase.HBaseTests;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.ixat.hbase.HBaseTests.LoadDataMR.HBaseMapper;

public class AnalyzeDataMR extends Configured implements Tool{

	public static void main(String[] args) throws Exception {
		//Now that we have stocks_mr table loaded with data, lets analyze the data and store the summary in another hbase table
		//create a table stocks_mr_analyzed
		// stocks_mr_analyzed
		//		|
		//		|
		//		stock_details
		//			|
		//			stock_name
		//		transaction
		//			|
		//			total_trade_volume
		//	in the shell>create 'stocks_mr_analyzed','stock_details','transaction'
		//	in the shell>scan 'stocks_mr_analyzed'
		int res = ToolRunner.run(new Configuration(), new AnalyzeDataMR(), args);
		System.exit(res);
	}
	
	public int run(String[] args)  throws Exception{
		String srctableName = "stocks_mr";
		String dsttableName = "stocks_mr_analyzed";
		if (args.length == 1) {
			srctableName = args[0];
		}
		if (args.length == 2) {
			dsttableName = args[1];
		}
	
		Configuration hconf = HBaseConfiguration.create();
		hconf.set("hbase.zookeeper.quorum","127.0.0.1");
		hconf.set("hbase.zookeeper.property.clientPort", "2181");
	       
	        
		Job job = Job.getInstance(hconf, "HBase_Stock_Analyzer");
		job.setJarByClass(AnalyzeDataMR.class);		
		Scan scan = new Scan();
		scan.setCaching(500);        // 1 is the default in Scan, which will be bad for MapReduce jobs
		scan.setCacheBlocks(false);  // don't set to true for MR jobs
		 
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
			      Bytes.toBytes("stock_details"),
			      Bytes.toBytes("stock_name"),
			      CompareOp.EQUAL,
			      new BinaryComparator(Bytes.toBytes("MSFT")));
			    
		  filter.setFilterIfMissing(true);
		

		scan.setFilter(filter);
		
		TableMapReduceUtil.initTableMapperJob(
				srctableName,        // input table
				scan,               // Scan instance to control CF and attribute selection
				MyHBaseMapper.class,     // mapper class
				Text.class,         // mapper output key
				LongWritable.class,  // mapper output value
				job);
		
		TableMapReduceUtil.initTableReducerJob(
				dsttableName,        // output table
				MyHBaseReducer.class,    // reducer class
				job);
		
		job.setNumReduceTasks(1);   // at least one, adjust as required
		
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static class MyHBaseMapper extends TableMapper<Text, LongWritable>  {

		 	private Text stock_name = new Text();
		 	private LongWritable tv = new LongWritable();

	   	public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
	        	String val = new String(value.getValue(Bytes.toBytes("stock_details"), Bytes.toBytes("stock_name")));
	        	stock_name.set(val);     
	          	
	        	val = new String(value.getValue(Bytes.toBytes("transaction"), Bytes.toBytes("trade_volume")));
	        	tv.set(Long.parseLong(val));
	        	System.out.println("MAPPER ===> " + stock_name + "\t" + tv);
	        	context.write(stock_name, tv);
	   	}
	}
	
	public static class MyHBaseReducer extends TableReducer<Text, LongWritable, ImmutableBytesWritable>  {

	 	public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
	    		long i = 0;
	    		for (LongWritable val : values) {
	    			i += val.get();
	    		}
	    		Put put = new Put(Bytes.toBytes(key.toString()));
	    		put.add(Bytes.toBytes("stock_details"), Bytes.toBytes("stock_name"), Bytes.toBytes(key.toString()));
	    		put.add(Bytes.toBytes("transaction"), Bytes.toBytes("total_trade_volume"), Bytes.toBytes(i));
	        	System.out.println("REDUCER ===> " + key + "\t" + i);

	    		context.write(null, put);
	    		
	   	}
	}
	    
}
