package com.ixat.hbase.HBaseTests;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;

public class SelectTest {

	public static void main(String[] args) throws Exception {
		Configuration hconf = HBaseConfiguration.create();
		hconf.set("hbase.zookeeper.quorum","127.0.0.1");
		hconf.set("hbase.zookeeper.property.clientPort", "2181");
	       
		HTable table = new HTable(hconf, "stocks_mr");

	        
		Scan scan = new Scan();
		
		  SingleColumnValueFilter filter = new SingleColumnValueFilter(
			      Bytes.toBytes("stock_details"),
			      Bytes.toBytes("stock_name"),
			      CompareOp.EQUAL,
			      new BinaryComparator(Bytes.toBytes("MSFT")));
			    
		  filter.setFilterIfMissing(true);
		scan.setFilter(filter);
			    
		ResultScanner scanner = table.getScanner(scan);

	    for (Result result = scanner.next(); result != null; result = scanner.next()){
	      System.out.println("Found row : StockName:" + new String(result.getValue(Bytes.toBytes("stock_details"),Bytes.toBytes("stock_name"))) +
	    		  new String(result.getValue(Bytes.toBytes("transaction"),Bytes.toBytes("trade_volume"))));
	    }
	    
	    scanner.close();

		
	}


}
