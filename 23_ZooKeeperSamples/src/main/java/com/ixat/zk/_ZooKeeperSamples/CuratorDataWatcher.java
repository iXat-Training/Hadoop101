package com.ixat.zk._ZooKeeperSamples;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorDataWatcher {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		RetryPolicy rp=new ExponentialBackoffRetry(1000, 3);//Retry mechanism
		  Builder builder = CuratorFrameworkFactory.builder().connectString("localhost:2181,localhost:2182:,localhost:2183")
				  .connectionTimeoutMs(5000)
				  .sessionTimeoutMs(5000)
				  .retryPolicy(rp);

		  CuratorFramework zkclient = builder.build();
		  zkclient.start();
		  
		  String data=new String(zkclient.getData().forPath("/zktests"));
		  System.out.println(data);
		  final  NodeCache cache = new NodeCache(zkclient, "/zktests");
		  cache.getListenable().addListener(new NodeCacheListener() {
			
		
			public void nodeChanged() throws Exception {
				// TODO Auto-generated method stub
				System.out.println(new String(cache.getCurrentData().getData()));
			}
		  });
		  
		  cache.start();
		  Thread.sleep(Integer.MAX_VALUE); //again, do something better here
	}	
}
