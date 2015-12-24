package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class ReadHDFS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Configuration config = new Configuration();
			FileSystem fs = FileSystem.get( new URI("hdfs://localhost:9000"),config);
			FSDataInputStream dis =  fs.open(new Path("/test/employees.txt"));
			BufferedReader in = new BufferedReader( new InputStreamReader( dis));
			
			String line = null;
			
			while( (line=in.readLine())!=null){
				System.out.println(line);
			}
		
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
