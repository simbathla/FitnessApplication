package com.fitness.activityservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.activityservice.model.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessageToTopic(Activity activity) throws JsonProcessingException {
        String jsonPayload = objectMapper.writeValueAsString(activity);
        CompletableFuture<SendResult<String, Object>> future = template.send(topic, jsonPayload);

        future.whenComplete((result, ex) -> {
            if(ex == null){
                System.out.println("Sent message=[ " + jsonPayload + "] " + result.getRecordMetadata().offset());
            }
            else{
                log.error("Unable to send Activity: {} due to: {}",
                        jsonPayload, ex.getMessage());
            }
        });
    }
}
