package com.ixat.zk._ZooKeeperSamples;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.ZooKeeper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	  ZooKeeper zoo =  new ZooKeeper("localhost:2181",3000, null);
 		 
		  List<String> childs = zoo.getChildren("/",false);
		  for(String znode: childs)
			  System.out.println(znode);
		  zoo.close();
    }
}
