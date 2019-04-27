package com.example.template.example;

import com.example.template.DeliveryRepository;
import com.example.template.OrderPlaced;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class SampleService {

	@Autowired
    private KafkaTemplate kafkaTemplate;
	
	@Autowired
    private RestTemplate restTemplate;

	
	public SampleUser eventCall(String name) {
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

	@Autowired
    DeliveryRepository deliveryRepository;
	
	@KafkaListener(topics = "topic")
	//	@KafkaListener(topics = "${topic.orderTopic}")
	public void sampleReceiveEvent(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {


		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OrderPlaced orderPlaced = null;
		try {
			orderPlaced = objectMapper.readValue(message, OrderPlaced.class);

			if (!OrderPlaced.class.getSimpleName().equals(orderPlaced.getType())) return;

			System.out.println("subscriber : " + message);
//			Optional<Delivery> delivery = deliveryRepository.findAllById();
//			delivery....

//
//			Delivery delivery = new Delivery();
//			delivery.setDeliveryAddress(orderPlaced.getType());
//			deliveryRepository.save(delivery)



		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public SampleUser restCall(long id) {
		String url = "http://35.243.123.46:8080/userInfoes/" + id;
		ResponseEntity<SampleUser> re = restTemplate.getForEntity(url, SampleUser.class);
		
//		this.findByUser(id);
		
		return re.getBody();
	}
	


	
}
