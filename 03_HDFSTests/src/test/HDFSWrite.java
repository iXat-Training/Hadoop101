package test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class HDFSWrite {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Configuration config = new Configuration();
			config.set("fs.defaultFS", "hdfs://127.0.0.1:9000");
			FileSystem fs = FileSystem.get( new URI("hdfs://localhost:9000"),config);
			FSDataOutputStream stream = fs.create(new Path("/test/employees.txt"));
			
			
			PrintWriter ps = new PrintWriter( new OutputStreamWriter(stream));
			
			for(int i =1;i<=100;i++){
				ps.println("Employee#" + i);
				
			}
		
			ps.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
