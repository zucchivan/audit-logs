package com.zucchivan.auditlogs.kafka;

import com.zucchivan.auditlogs.data.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class MessageListener {

    @Autowired
    LogRepository logRepository;

    @KafkaListener(topics = "logs", containerFactory = "logKafkaListenerContainerFactory")
    public void listenGroupFoo(String message) {
    }
}
