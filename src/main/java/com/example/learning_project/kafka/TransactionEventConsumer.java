package com.example.learning_project.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventConsumer {

    @KafkaListener(topics = "transaction-events", groupId = "transaction_group")
    public void listen(String message) {
        System.out.println("📥 Kafka Message Received: " + message);
    }
}
