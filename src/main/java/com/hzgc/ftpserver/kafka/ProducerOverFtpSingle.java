package com.hzgc.ftpserver.kafka;


import com.hzgc.ftpserver.util.Utils;
import org.apache.ftpserver.util.IoUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ProducerOverFtpSingle {
    private static Logger log = Logger.getLogger(ProducerOverFtpSingle.class);
    private static KafkaProducer kafkaProducer;
    private Properties kafkaPropers = new Properties();
    private FileInputStream fis;
    private static  String PICTURE = "picture";
    private static  String FACE = "face";
    private static  String JSON = "json";
    public ProducerOverFtpSingle() {
        try {
            File file = Utils.loadResourceFile("kafka-over-ftp.properties");
            this.fis = new FileInputStream(file);
            this.kafkaPropers.load(fis);
            PICTURE = kafkaPropers.getProperty("topic-picture");
            FACE = kafkaPropers.getProperty("topic-face");
            JSON = kafkaPropers.getProperty("topic-json");
            if (kafkaPropers != null) {
                kafkaProducer = new KafkaProducer(kafkaPropers);
                log.info("Create KafkaProducer successfull");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(fis);
        }
    }

    public void sendKafkaMessage(final String topic, final String key, final byte[] value) {
        long startTime = System.currentTimeMillis();
        if (kafkaPropers != null) {
            kafkaProducer.send(new ProducerRecord<String, byte[]>(topic, key, value),
                    new ProducerCallBack(startTime, key));
        }
    }

    public void closeProducer() {
        if (null != kafkaProducer) {
            kafkaProducer.close();
        }
    }

    public static final ProducerOverFtpSingle getInstance() {
        return LazyHandler.instanc;
    }

    public static class LazyHandler {
        private static final ProducerOverFtpSingle instanc = new ProducerOverFtpSingle();
    }

    public static String getPicture() {
        return PICTURE;
    }

    public static String getFace() {
        return FACE;
    }

    public static String getJson() {
        return JSON;
    }
}