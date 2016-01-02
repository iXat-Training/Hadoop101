package com.ixat.ioformats.custom;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DataOutputBuffer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit; 

public class EmployeeInputFormat extends FileInputFormat<LongWritable, EmployeeWritable>{
		public static final String START_TAG_KEY = "<employee>";
	    public static final String END_TAG_KEY = "</employee>";
	 
	    
	    public RecordReader<LongWritable, EmployeeWritable> createRecordReader(
	            InputSplit split, TaskAttemptContext context) {
	        return new EmployeeRecordReader();
	    }

	    public static class EmployeeRecordReader extends
        RecordReader<LongWritable, EmployeeWritable> {

	    	 private byte[] startTag;
	         private byte[] endTag;
	         private long start;
	         private long end;
	         private FSDataInputStream fsin;
	         private DataOutputBuffer buffer = new DataOutputBuffer();
	         private LongWritable key = new LongWritable();
	         private EmployeeWritable value = new EmployeeWritable();
	         
			@Override
			public void close() throws IOException {
				 fsin.close();
				
			}

			@Override
			public LongWritable getCurrentKey() throws IOException,
					InterruptedException {
				// TODO Auto-generated method stub
				return key;
			}

			@Override
			public EmployeeWritable getCurrentValue() throws IOException,
					InterruptedException {
				// TODO Auto-generated method stub
				return value;
			}

			@Override
			public float getProgress() throws IOException, InterruptedException {
				return (fsin.getPos() - start) / (float) (end - start);
			}

			@Override
			public void initialize(InputSplit is, TaskAttemptContext ta)
					throws IOException, InterruptedException {
				FileSplit fileSplit = (FileSplit) is;
	            String START_TAG_KEY = "<employee>";
	            String END_TAG_KEY = "</employee>";
	            startTag = START_TAG_KEY.getBytes("utf-8");
	            endTag = END_TAG_KEY.getBytes("utf-8");
	 
	            start = fileSplit.getStart();
	            end = start + fileSplit.getLength();
	            Path file = fileSplit.getPath();
	 
	            FileSystem fs = file.getFileSystem(ta.getConfiguration());
	            fsin = fs.open(fileSplit.getPath());
	            fsin.seek(start);
				
			}

			@Override
			public boolean nextKeyValue() throws IOException,
					InterruptedException {
				if (fsin.getPos() < end) {
	                if (readUntilMatch(startTag, false)) {
	                    try {
	                        buffer.write(startTag);
	                        if (readUntilMatch(endTag, true)) {
	 
	                            value.setFieldsfromXML( new String(buffer.getData(), 0, buffer.getLength()));
	                            key.set(fsin.getPos());
	                            return true;
	                        }
	                    } finally {
	                        buffer.reset();
	                    }
	                }
	            }
	            return false;
			}

			private boolean readUntilMatch(byte[] match, boolean withinBlock)
	                throws IOException {
	            int i = 0;
	            while (true) {
	                int b = fsin.read();
	 
	                if (b == -1)
	                    return false;
	 
	                if (withinBlock)
	                    buffer.write(b);
	 
	                if (b == match[i]) {
	                    i++;
	                    if (i >= match.length)
	                        return true;
	                } else
	                    i = 0;
	 
	                if (!withinBlock && i == 0 && fsin.getPos() >= end)
	                    return false;
	            }
	        }
	    }
}
