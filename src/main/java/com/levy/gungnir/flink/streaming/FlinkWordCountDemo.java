package com.levy.gungnir.flink.streaming;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;

import java.util.Properties;


public class FlinkWordCountDemo {

    public void wordCount(){
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // checkpoint every 5000 msecs
//        env.enableCheckpointing(5000);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "172.31.13.10:9093,172.31.13.11:9093,172.31.13.12:9093");
        properties.setProperty("auto.offset.reset", "latest");
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("group.id", "test");


        FlinkKafkaConsumer010<String> myConsumer = new FlinkKafkaConsumer010<String>("test", new SimpleStringSchema(), properties);
        myConsumer.setStartFromEarliest();     // start from the earliest record possible
        myConsumer.setStartFromLatest();       // start from the latest record
        myConsumer.setStartFromGroupOffsets(); // the default behaviour



        DataStream<Tuple2<String, Integer>> dataStream =env.addSource(myConsumer)
                .flatMap(new LineSplitter()).keyBy(0).sum(1);
        dataStream.print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        FlinkWordCountDemo test = new FlinkWordCountDemo();
        test.wordCount();
    }
    public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // normalize and split the line
            String[] tokens = value.toLowerCase().split("\\-");

            // emit the pairs
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<String, Integer>(token, 1));
                }
            }
        }
    }
}