package com.syscomz;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    // if I want to use custom object should add the custom message into trusted package
    @KafkaListener(topics = "syscomz", groupId = "myGroupId")
    void listener(Message data) {
        System.out.println("Listener received data: " + data.message() + " :)");
    }

    // configuration for message
    // @KafkaListener(topics = "syscomz", groupId = "myGroupId")
    // void listener(String data) {
    //     System.out.println("Listener received data: " + data + " :)");
    // }

}
