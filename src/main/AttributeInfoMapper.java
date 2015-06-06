package main;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

public class AttributeInfoMapper extends Mapper<TextArrayWritable, IntWritable, Text, AttributeCounterWritable> {

    public void map(TextArrayWritable key, IntWritable value, Context context) throws IOException, InterruptedException {

        Writable[] key_values = key.get();
        Text[] attributes_and_class = new Text[key_values.length];
        for (int i = 0; i < key_values.length; ++i) {
            attributes_and_class[i] = (Text) key_values[i];
        }

        Text classification = attributes_and_class[attributes_and_class.length - 1];

        for (Integer i = 0; i < attributes_and_class.length - 1; ++i) {
            context.write(new Text(i.toString()), new AttributeCounterWritable(attributes_and_class[i], classification, value));
        }

    }
}
