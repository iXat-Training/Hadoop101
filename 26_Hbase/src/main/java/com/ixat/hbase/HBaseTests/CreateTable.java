package com.ixat.hbase.HBaseTests;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class CreateTable {

	public static void main(String[] args) throws Exception {
		
		Configuration hBaseConfig = HBaseConfiguration.create();
        hBaseConfig.set("hbase.zookeeper.quorum","127.0.0.1");
        hBaseConfig.set("hbase.zookeeper.property.clientPort", "2181");
        
        HBaseAdmin admin = new HBaseAdmin(hBaseConfig);
      
        String tableName = "stocks_test_" + System.currentTimeMillis();
        HTableDescriptor tableDescriptor = new
        	      HTableDescriptor(TableName.valueOf(tableName));
        
        tableDescriptor.addFamily(new HColumnDescriptor("identification"));
        tableDescriptor.addFamily(new HColumnDescriptor("prices"));
        tableDescriptor.addFamily(new HColumnDescriptor("transactions"));

        admin.createTable(tableDescriptor);
        
        System.out.println( tableName + " Table created ");

        //now insert the data
        HTable hTable = new HTable(hBaseConfig, tableName);

        //lets insert 50 rows for MSFT
        
        Random r = new Random();
        
        for(int i=1;i<=50;i++){
        	
            Put p = new Put(Bytes.toBytes("row" + i)); 
            p.add(Bytes.toBytes("identification"), Bytes.toBytes("stock_name"), Bytes.toBytes("MSFT"));
            p.add(Bytes.toBytes("identification"), Bytes.toBytes("stock_exchange"), Bytes.toBytes("NYSE"));
            
            p.add(Bytes.toBytes("prices"), Bytes.toBytes("open"), Bytes.toBytes("44.55"));
            p.add(Bytes.toBytes("prices"), Bytes.toBytes("close"), Bytes.toBytes(r.nextDouble()*70));
            p.add(Bytes.toBytes("prices"), Bytes.toBytes("low"), Bytes.toBytes("43.2"));
            p.add(Bytes.toBytes("prices"), Bytes.toBytes("high"), Bytes.toBytes("78.9"));
            
            p.add(Bytes.toBytes("transactions"), Bytes.toBytes("trade_date"), Bytes.toBytes("31-DEC-2015"));
            p.add(Bytes.toBytes("transactions"), Bytes.toBytes("trade_volume"), Bytes.toBytes(r.nextLong()*100000));
            
            hTable.put(p);
            hTable.flushCommits();
            System.out.println("Inserted Row:" + i);
        }
 
        
        
        
	}

}
