package com.example.template.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Service
public class SampleService {

	@Autowired
    private KafkaTemplate kafkaTemplate;
	
	@Autowired
    private RestTemplate restTemplate;
	
	public SampleUser call(String name) {
		ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        SampleUser sampleUser = new SampleUser();
        try {
        	sampleUser.setId(1L);
        	sampleUser.setUsername(name);
            json = objectMapper.writeValueAsString(sampleUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        ProducerRecord producerRecord = new ProducerRecord<>("class-topic", json);
        kafkaTemplate.send(producerRecord);
        
        return sampleUser;
	}

	
	@KafkaListener(topics = "class-topic")
	public void sampleReceiveEvent(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {

		Gson gson = new Gson();
		SampleUser enrolled = gson.fromJson(message, SampleUser.class);
		System.out.println("subscriber : " + message);

	}


	public SampleUser restCall(String name) {
		String url = "http://localhost:8080/sample/" + name;
		ResponseEntity<SampleUser> re = restTemplate.getForEntity(url, SampleUser.class);
		return re.getBody();
	}
	
}
