 Apache Avro is a serialization framework, some of the advantages of using Avro are as following -


 *Schema evolution – Avro requires schemas when data is written or read. Most interesting is that you can use different schemas for serialization and deserialization, and Avro will handle the missing/extra/modified fields.

* Untagged data – Providing a schema with binary data allows each datum be written without overhead. The result is more compact data encoding, and faster data processing.

* Dynamic typing – This refers to serialization and deserialization without code generation. It complements the code generation, which is available in Avro for statically typed languages as an optional optimization.


Code demonstrates using Avro for Specific and GenericDatumReaders and Append to a avro file

Also, there is a .NET (C#) example which reads avro file written in Java.

For .NET code, install VS2015 community (or any edition) and open command prompt after running the java Users app(App.java)
cd to src\main\dotnet\AvroTestsCsharp\AvroTestsCsharp
run create_classes.bat
Compile and run the example in VS2015. 