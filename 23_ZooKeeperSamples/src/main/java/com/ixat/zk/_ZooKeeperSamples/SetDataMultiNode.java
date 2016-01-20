package com.ixat.zk._ZooKeeperSamples;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class SetDataMultiNode {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
			ZooKeeper zk = new ZooKeeper("localhost:2181,localhost:2182:,localhost:2183", 3000, null);
			try{
				zk.create("/zktests","TESTDATA".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}catch(Exception ex){
				//ex.printStackTrace();
				System.out.println("/ztests already exists...setting the data now...");
			}
			
			byte[] data = zk.getData("/zktests", null, null);
			System.out.println(new String(data));
			zk.setData("/zktests", ("This is test data set from java @ " + System.currentTimeMillis()).getBytes(),-1);
			zk.close();
	}
}
