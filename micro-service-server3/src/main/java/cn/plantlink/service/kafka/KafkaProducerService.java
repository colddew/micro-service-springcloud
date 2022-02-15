package cn.plantlink.service.kafka;

import cn.plantlink.pojo.KafkaMessage;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;

/**
 * @Author colddew
 * @Date 2022-02-13
 */
@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    @Scheduled(cron = "0/5 * * * * ?")
    public void send() throws Exception {

        long startTime = System.currentTimeMillis();

        String topic = "test-topic";
        String key = RandomStringUtils.randomAlphanumeric(10);

        KafkaMessage message = new KafkaMessage();
        message.setId(key);
        message.setContent("kafka test content");
        message.setDate(new Date());
        String data = JSON.toJSONString(message);

        kafkaTemplate.send(topic, key, data).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            long elapsedTime = System.currentTimeMillis() - startTime;

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("kafka send success, topic({}) message({}, {}) sent to partition({}), offset({}) in {} ms, timestamp is {}",
                        topic, key, message, result.getRecordMetadata().partition(), result.getRecordMetadata().offset(), elapsedTime, result.getRecordMetadata().timestamp());
            }

            @Override
            public void onFailure(Throwable throwable) {
                logger.info("kafka send fail, topic({}) message({}, {}): {}", topic, key, message, ExceptionUtils.getStackTrace(throwable));
            }
        });
    }
}
