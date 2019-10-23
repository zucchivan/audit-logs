package com.zucchivan.auditlogs.kafka;

import com.zucchivan.auditlogs.data.repository.LogRepository;
import com.zucchivan.auditlogs.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

public class MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);


    @Autowired
    LogRepository logRepository;

    @KafkaListener(id = "logs-listener", topicPattern = "*-logs")
    public void listenLogsTopics(@Payload Log log, @Headers MessageHeaders headers) {
        LOGGER.info("received log message: ", log.toString());

    }
}
