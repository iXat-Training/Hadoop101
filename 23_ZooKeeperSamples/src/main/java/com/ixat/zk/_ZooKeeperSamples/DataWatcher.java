package com.ixat.zk._ZooKeeperSamples;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class DataWatcher implements Watcher {
	
	private ZooKeeper instance;
	
	public DataWatcher(ZooKeeper zk){
		instance = zk;
	}
	
	public static void main(String args[]) throws Exception{
		ZooKeeper zk = new ZooKeeper("localhost:2181", 2000, null);
		DataWatcher watcher = new DataWatcher(zk);
		
		watcher.printData();
		
		//this is bad, just for demontsrtation. choose a better way rather than running an infinite dumb loop
		while(true){
			Thread.sleep(2000);
		}
	}
	
	
	
	public void process(WatchedEvent we) {
		if(we.getType() == Event.EventType.NodeDataChanged)
			printData();
		
	}
	
	
	
	public void printData() {
		try {
			byte[] bytes = instance.getData("/zktests",this, null);
			System.out.println(new String(bytes));
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
