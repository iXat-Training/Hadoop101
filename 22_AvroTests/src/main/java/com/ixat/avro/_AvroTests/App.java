package com.ixat.avro._AvroTests;


import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import com.ixat.avro.model.User;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    
    	doCodeGen();
    	doGeneric();
    	
    }
    
    private static void doGeneric() throws Exception{
    	//Schema schema = new Schema.Parser().parse(new File("user.avsc"));
    	DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(null);
    	DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File("users.avro"), datumReader);
    	GenericRecord user = null;
    	while (dataFileReader.hasNext()) {
        	user = dataFileReader.next(user);
	    	System.out.println("Desrialized via Generic==>" + user);
    	}
    }
    
    private static void doCodeGen() throws Exception{
    	
    	User user1 = new User();
    	user1.setName("Alyssa");
    	user1.setFavoriteNumber(256);
    	
    	User user2 = new User("Ben", 7, "red");

    	User user3 = User.newBuilder()
    	             .setName("Charlie")
    	             .setFavoriteColor("blue")
    	             .setFavoriteNumber(null)
    	             .build();
    	
    	
    	DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
    	DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
    	dataFileWriter.create(user1.getSchema(), new File("users.avro"));
    	dataFileWriter.append(user1);
    	dataFileWriter.append(user2);
    	dataFileWriter.append(user3);
    	dataFileWriter.close();
    	
    	
    	//Now deserialize
    	DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
    	DataFileReader<User> dataFileReader = new DataFileReader<User>(new File("users.avro"), userDatumReader);
    	User user = null;
    	while (dataFileReader.hasNext()) {
	    	// Reuse user object by passing it to next(). This saves us from
	    	// allocating and garbage collecting many objects for files with
	    	// many items.
    		user = dataFileReader.next(user);
    		System.out.println("Desrialized via Code gen==>" + user);
    	}
    }
}

