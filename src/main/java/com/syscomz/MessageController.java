package com.syscomz;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    // private final KafkaTemplate<String, String> kafkaTemplate; // configuration for String

    public MessageController(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void publish(@RequestBody MessageRequest request) {
        Message message = new Message(request.message(), LocalDateTime.now());
        kafkaTemplate.send("syscomz", message);
    }

}
