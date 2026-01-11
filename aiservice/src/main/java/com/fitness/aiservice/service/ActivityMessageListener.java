package com.fitness.aiservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    private final ActivityAIService aiService;
    private final RecommendationRepository recommendationRepository;

    @KafkaListener(topics="${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.groupId}")
    public void consume(String message){
        log.info("Consumer consumed the message: {}", message);
        try{
            Activity activity = objectMapper.readValue(message, Activity.class);
            log.info("Parsed Activity for User: {}", activity.getUserId());

            Recommendation recommendation = aiService.generateRecommendation(activity);
            recommendationRepository.save(recommendation);
            log.info("Generated Recommendation:{}", recommendation);

        }catch(JsonProcessingException ex){
            log.error("Failed to parse JSON: {}", message, ex);
        }
    }
}
