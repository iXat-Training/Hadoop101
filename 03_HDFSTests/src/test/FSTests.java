package test;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FSTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try{
			
			Configuration config = new Configuration();
			
			//add core-site.xml
			//config.addResource(new Path("C:\\hdp2.3\\etc\\hadoop\\core-site.xml"));
			
			//or add hdfs-site to classpath.
			//new URI("hdfs://localhost:9000"),
			
			FileSystem fs = FileSystem.get( new URI("hdfs://localhost:9000"),config);
			FileStatus[] statuses = fs.listStatus(new Path("/test"));
			for(FileStatus status: statuses){
				System.out.println(status.getPath() + " BlockSize:" + status.getBlockSize()/1024/1024 + "MB ReplicationFactor:" + status.getReplication() + " FileSize:" + status.getLen());
			}
			
		}catch(Exception ex){
			
			System.out.println(ex);
		}
	}

}
