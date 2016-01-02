 Examples to demonstrate - 
 1. CombinedTextInputFormat
		 	--Split a big file into parts (just to simulate an injestor writing multiple files)
		   	split -l 100 -d Posts.xml split_
		
			--copy the files to HDFS
			hdfs dfs -mkdir /so/parts
			hdfs dfs -put split* /so/parts
		    hdfs dfs -ls /so/parts


		 yarn jar ioformat.jar com.ixat.ioformats.inputformats.CombineFormatTest /so/parts/* /so/parts/result1

		 yarn jar ioformat.jar com.ixat.ioformats.inputformats.CombineFormatTest /so/parts/* /so/parts/result2 sequence 

		 yarn jar ioformat.jar com.ixat.ioformats.inputformats.CombineFormatTest /so/parts/* /so/parts/result3 sequence compress


		 --Examine the sizes of Output folders

		 hdfs dfs -ls -h /so/parts/result1
		 hdfs dfs -ls -h /so/parts/result2
		 hdfs dfs -ls -h /so/parts/result3


2. NLInputFormat

		 yarn jar ioformat.jar com.ixat.ioformats.inputformats.NLFormatTest /so/posts.xml /so/nltest
		 --  check thenumber of java processes when the job is running, also check the split counter
		     ps -eaf | grep java | wc -l

		 --check the number of files generated
		 hdfs dfs -ls -h /so/nltest

3. A Custom InputFormat to read off from an XML, the records are not delimited by a new line instead the record end marker is an </employee> element.
