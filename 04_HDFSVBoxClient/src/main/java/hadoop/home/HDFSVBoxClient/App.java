package hadoop.home.HDFSVBoxClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
        	//System.setProperty("HADOOP_USER_NAME","hduser");
        	//System.setProperty("java.net.preferIPv4Stack", "true");
        	
        	Configuration config = new Configuration();
			FileSystem fs = FileSystem.get( new URI("hdfs://localhost:9000"),  config);
			//we have written a sample text file in our VBox HDFS in the path /test/a.txt
			
			//FSDataInputStream instream = fs.open(new Path("/test/a.txt"));
			//BufferedReader reader = new BufferedReader( new InputStreamReader(instream));
			BufferedReader reader = new BufferedReader( new InputStreamReader(fs.open(new Path("/test/a.txt"))));
			String line = null;
			while (   (line=reader.readLine())!=null){
				System.out.println(line);
			}
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
