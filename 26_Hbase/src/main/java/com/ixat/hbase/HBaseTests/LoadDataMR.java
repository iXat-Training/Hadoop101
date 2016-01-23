package com.ixat.hbase.HBaseTests;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class LoadDataMR extends Configured implements Tool{

	public static void main(String[] args) throws Exception {
		//in the hbase shell create a stocks_mr table with 2CF's
		//stocks_mr
		//		|
		//		|
		//		stock_details
		//			|
		//			stock_name
		//		price
		//			|
		//			trade_price
		//		transaction
		//			|
		//			trade_volume
		//			trade_date
		//
		//	in the shell>create 'stocks_mr','stock_details','price','transaction'
		//	in the shell>scan 'stocks_mr'
		//
		// 	load the stocks.csv to HDFS
		//		 hdfs dfs -put stocks.csv /stocks
		//  do a HADOOP_CLASSPATH with hbase jars ==> export HADOOP_CLASSPATH=`hbase classpath`

		//run the sample,
		// open hbase shell
		//  >scan 'stocks_mr'
		//  >scan 'stocks_mr',{COLUMNS=>['stock_details:stock_name','price:trade_price','transaction:trade_volume']}
		//  scan only MSFT
		//	>scan 'stocks_mr',{COLUMNS=>['stock_details:stock_name','price:trade_price','transaction:trade_volume'], FILTER=> "MultipleColumnPrefixFilter('stock_name') AND  ValueFilter( = 'binary:MSFT')"}
		
		int res = ToolRunner.run(new Configuration(), new LoadDataMR(), args);
		System.exit(res);
	}

	public int run(String[] args)  throws Exception{
		String tableName = "stocks_mr";
		String inFile = "/stocks/stocks.csv";
		
		if (args.length == 1) {
			inFile = args[0];
		}
		if (args.length == 2) {
			tableName = args[1];
		}
		
		Configuration hconf = HBaseConfiguration.create();
		hconf.set("hbase.zookeeper.quorum","127.0.0.1");
		hconf.set("hbase.zookeeper.property.clientPort", "2181");
	       
	        
		Job job = Job.getInstance(hconf, "Load Stocks2HBase_Infile:" + inFile + " HBaseTable:" + tableName);
		job.setJarByClass(LoadDataMR.class);		
		FileInputFormat.setInputPaths(job, new Path(inFile));
		
		
		job.setMapperClass(HBaseMapper.class);
		
		job.setMapOutputKeyClass(ImmutableBytesWritable.class);
		job.setMapOutputValueClass(Put.class);
		
		TableMapReduceUtil.initTableReducerJob(tableName, null, job);
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	static class HBaseMapper  extends Mapper<LongWritable, Text, ImmutableBytesWritable,Put> 
	{
		
		public void map(LongWritable key, Text line, Context context) {
			String [] values = line.toString().split(",");
			if(values.length != 7) {
				System.out.println("err values.length!=5 len:"+values.length);
				System.out.println("input string is:"+line);
				return;
			}
			//we just need stockname, price, date and volume
			String stock_name = values[0];
			String price = values[3]; //lets consider the closing price (the th field)
			String date = values[1];
			String volume = values[6];
			String rowID = java.util.UUID.randomUUID().toString();
			Put put = new Put(Bytes.toBytes(rowID));
			
			String cf = "stock_details";
			String cn = "stock_name";
			put.add(Bytes.toBytes(cf), Bytes.toBytes(cn), Bytes.toBytes(stock_name));
			
			cf = "price";
			cn = "trade_price";
			put.add(Bytes.toBytes(cf), Bytes.toBytes(cn), Bytes.toBytes(price));
			

			cf = "transaction";
			cn = "trade_volume";
			put.add(Bytes.toBytes(cf), Bytes.toBytes(cn), Bytes.toBytes(volume));

			cn = "trade_date";
			put.add(Bytes.toBytes(cf), Bytes.toBytes(cn), Bytes.toBytes(date));
			try {
				context.write(new ImmutableBytesWritable(Bytes.toBytes(rowID)), put);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}

