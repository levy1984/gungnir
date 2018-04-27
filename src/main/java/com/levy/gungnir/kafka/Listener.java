package com.levy.gungnir.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Listener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @param record

    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<?, ?> record) {
        logger.info("kafka的key: " + record.key());
        logger.info("kafka的value: " + record.value().toString());
    }
     */
}
