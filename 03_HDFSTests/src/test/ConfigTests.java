package test;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

public class ConfigTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try{
			
			Configuration config = new Configuration();
			//Do using a classpath
			
			//or do a manual override
			config.addResource(new Path("C:\\hdp2.3\\etc\\hadoop\\core-site.xml"));
			
			 Iterator<Map.Entry<String,String>> iterator = config.iterator();
			while(iterator.hasNext())  {
				Entry<String, String> pair = iterator.next();
				System.out.println(pair.getKey() + ":" + pair.getValue());
			}
			
		}catch(Exception ex){
			System.out.println(ex);
		}
	}

}
