package com.syscomz.config;

import com.syscomz.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);    // configuration for String
        // props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class); // configuration for String
        return props;
    }

    // If I want to use custom object then the value of the map should be the custom object
    // If I want to use String object then the value of the map should be the String object
    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        JsonDeserializer<Message> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("com.syscomz"); // add all classes into this package as a trusted source, this is needed because we want to use a custom object (in this case Message) into KafkaListeners class. For trust all use (*)

        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>());
        // return new DefaultKafkaConsumerFactory<>(consumerConfig()); // configuration for String
    }

    // Receives all messages of all topics, or partitions on a single thread
    // If I want to use custom object then the value of the map should be the custom object
    // If I want to use String object then the value of the map should be the String object
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Message>> factory(
            ConsumerFactory<String, Message> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public RecordMessageConverter converter() {
        return new JsonMessageConverter();
    }

}
