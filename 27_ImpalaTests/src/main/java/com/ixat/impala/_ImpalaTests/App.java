package com.ixat.impala._ImpalaTests;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	Connection con = null;

		try {

			Class.forName("org.apache.hive.jdbc.HiveDriver");

			con = DriverManager.getConnection("jdbc:hive2://localhost:21050/;auth=noSasl");

			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select stock_name, trade_date, traded_volume from stocks");
			

			System.out.println("\n== Begin Query Results ======================");

			int i =0;
			// print the results to the console
			while (rs.next()) {
				// the example query returns one String column
				System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
				i++;
			}

			System.out.println("== End Query Results =========\nFetched " + i + " Results\n\n");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(con!=null)
				con.close();
		}
    }
}
