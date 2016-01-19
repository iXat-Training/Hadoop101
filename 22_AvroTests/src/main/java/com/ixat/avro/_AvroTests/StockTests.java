package com.ixat.avro._AvroTests;


import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import com.ixat.avro.model.Stock;

public class StockTests {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		writeStocks("stocks.csv","stocks.avro");
		readStocks("stocks.avro");
	
		Stock s = new Stock();
		s.setSymbol("INTC");
		s.setTradeDate("31-Dec-16");
		s.setOpenPrice( 50.1);
		s.setClosePrice( 53.2);
		s.setHiPrice( 55.2);
		s.setLowPrice( 51.1);			
		s.setTradeVolume(600000000L);
	
		appendStock(s,"stocks.avro");
		readStocks("stocks.avro");
		readStocksGeneric("stocks.avro");
		readStocksGenericProjection("stocks.avro");
		readStocksGenericEvolution("stocks.avro");
	}
	
	
	public static void appendStock( Stock s, String fileName) throws Exception{
		DatumWriter<Stock> stockDatumWriter = new SpecificDatumWriter<Stock>(Stock.class);
    	DataFileWriter<Stock> dataFileWriter = new DataFileWriter<Stock>(stockDatumWriter);
    	dataFileWriter.appendTo(new File(fileName));
    	dataFileWriter.append(s);
    	dataFileWriter.close();
	}
	public static void writeStocks(String csv, String fileName) throws Exception{
		
		Stock temp = new Stock();
		
		DatumWriter<Stock> stockDatumWriter = new SpecificDatumWriter<Stock>(Stock.class);
    	DataFileWriter<Stock> dataFileWriter = new DataFileWriter<Stock>(stockDatumWriter);
    	dataFileWriter.create(temp.getSchema(), new File(fileName));
    	
		List<Stock> stocks = new ArrayList<Stock>();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(csv)));
		String line = null;
		int i=0;
		while((line = in.readLine())!= null){
			String[] st = line.split(",");
			Stock s = new Stock();
			s.setSymbol(st[0]);
			s.setTradeDate(st[1]);
			s.setOpenPrice( Double.valueOf(st[2]));
			s.setClosePrice( Double.valueOf(st[3]));
			s.setHiPrice( Double.valueOf(st[4]));
			s.setLowPrice( Double.valueOf(st[5]));			
			s.setTradeVolume(Long.valueOf(st[6]));
			dataFileWriter.append(s);
			i++;
		}

		dataFileWriter.close();
		in.close();
		System.out.println("Wrote " + i + " Stocks...");
	}
	public static void readStocks(String fileName) throws Exception{
		DatumReader<Stock> stockDatumReader = new SpecificDatumReader<Stock>(Stock.class);
    	DataFileReader<Stock> dataFileReader = new DataFileReader<Stock>(new File(fileName), stockDatumReader);
    	Stock s = null;
    	int i =0;
    	while (dataFileReader.hasNext()) {
	    	
    		s = dataFileReader.next(s);
    		//System.out.println( s);
    		i++;
    	}
    	dataFileReader.close();
		System.out.println("Read " + i + " Stocks...");

	}

	public static void readStocksGeneric(String fileName) throws Exception{
		DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(null);
    	DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(fileName), datumReader);
    	GenericRecord stock = null;
    	int i =0;
    	while (dataFileReader.hasNext()) {
    		stock = dataFileReader.next(stock);
	    	//System.out.println("Desrialized via Generic==>" + stock);
	    	i++;
    	}	
    	dataFileReader.close();
		System.out.println("Read " + i + " Stocks...");

	}
	
	public static void readStocksGenericProjection(String fileName) throws Exception{
		String str = "";
		str += "{\"namespace\": \"com.ixat.avro.model\"," ;
		str += " \"type\": \"record\"," ;
		str += " \"name\": \"Stock\"," ;
		str += " \"fields\": [" ;
		str += "     {\"name\": \"symbol\", \"type\": \"string\"}," ;
		str += "     {\"name\": \"trade_date\",  \"type\": \"string\"},    " ;
		str += "	 {\"name\": \"hi_price\",  \"type\": \"double\"}," ;
		str += "	 {\"name\": \"trade_volume\",  \"type\": \"long\"}" ;
		str += " ]" ;
		str += "}" ;
		
		Schema schema = new Schema.Parser().parse(str);
    	
		DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(null , schema);
    	DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(fileName), datumReader);
    	GenericRecord stock = null;
    	int i =0;
    	while (dataFileReader.hasNext()) {
    		stock = dataFileReader.next(stock);
	    	//System.out.println("Projected via Generic ==>" + stock);
	    	i++;
    	}	
    	dataFileReader.close();
		System.out.println("Read " + i + " Stocks...");

	}
	
	public static void readStocksGenericEvolution(String fileName) throws Exception{
		String str = "";
		str += "{\"namespace\": \"com.ixat.avro.model\"," ;
		str += " \"type\": \"record\"," ;
		str += " \"name\": \"Stock\"," ;
		str += " \"fields\": [" ;
		str += "     {\"name\": \"symbol\", \"type\": \"string\"}," ;
		str += "     {\"name\": \"trade_date\",  \"type\": \"string\"}," ;
		str += "     {\"name\": \"open_price\",  \"type\": \"double\"}," ;
		str += "	 {\"name\": \"close_price\",  \"type\": \"double\"}," ;
		str += "	 {\"name\": \"hi_price\",  \"type\": \"double\"}," ;
		str += "	 {\"name\": \"low_price\",  \"type\": \"double\"}," ;
		str += "	 {\"name\": \"trade_state\",  \"type\": [\"string\",\"null\"], \"default\":\"NORMAL\"}," ;
		str += "	 {\"name\": \"trade_volume\",  \"type\": \"long\" , \"order\":\"descending\"}" ;
		str += " ]" ;
		str += "}" ;
		Schema schema = new Schema.Parser().parse(str);
		DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(null , schema);
    	DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(fileName), datumReader);
    	GenericRecord stock = null;
    	int i =0;
    	while (dataFileReader.hasNext()) {
    		stock = dataFileReader.next(stock);
	    	System.out.println("Evolved via Generic ==>" + stock);
	    	i++;
    	}	
    	dataFileReader.close();
		System.out.println("Read " + i + " Stocks...");
	}
	
}
