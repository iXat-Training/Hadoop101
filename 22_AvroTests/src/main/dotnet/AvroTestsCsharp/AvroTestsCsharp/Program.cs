using Avro.File;
using Avro.Generic;
using Avro.IO;
using example.avro;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AvroTestsCsharp
{
    class Program
    {
        static void Main(string[] args)
        {

            String schema = new StreamReader("user.avsc").ReadToEnd();

            Avro.Schema avschema = Avro.Schema.Parse(schema);


            DatumReader<User> reader = new Avro.Specific.SpecificDatumReader<User>(avschema, avschema);
            Stream inStr = new FileStream("users.avro", FileMode.Open);
            IFileReader<User> dataFileReader = DataFileReader<User>.OpenReader(inStr, avschema);
         
            while (dataFileReader.HasNext())
            {
                User record = dataFileReader.Next();
                Console.WriteLine("Specific Obj Read ==>" + record.name + ":" + record.favorite_color + ":" + record.favorite_number);
            
            }
            inStr.Close();

            inStr = new FileStream("users.avro", FileMode.Open);
            DatumReader<GenericRecord> reader2 = new Avro.Generic.GenericDatumReader<GenericRecord>(avschema, avschema);
            IFileReader<GenericRecord> gdataFileReader = DataFileReader<GenericRecord>.OpenReader(inStr, avschema);
            while (gdataFileReader.HasNext())
            {
                GenericRecord grecord = gdataFileReader.Next();
                Console.WriteLine("Generic mode of read==>" + grecord["name"] + ":" + grecord["favorite_color"] + ":"  +grecord["favorite_number"]);
            }

            Console.Write("Hit ENTER to Close:");
            Console.ReadLine();


        }
    }
}
