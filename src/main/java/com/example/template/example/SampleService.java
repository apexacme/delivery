package com.example.template.example;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
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
	
	@Autowired
	private SampleUserRepository sampleUserRepository;
	
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

	
	@KafkaListener(topics = "class-topic")
	public void sampleReceiveEvent(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {

		Gson gson = new Gson();
		SampleUser enrolled = gson.fromJson(message, SampleUser.class);
		System.out.println("subscriber : " + message);

	}


	public SampleUser restCall(long id) {
		String url = "http://35.243.123.46:8080/userInfoes/" + id;
		ResponseEntity<SampleUser> re = restTemplate.getForEntity(url, SampleUser.class);
		
//		this.findByUser(id);
		
		return re.getBody();
	}
	
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<SampleUser> findByUser(long id) {
		
		ArrayList<String> test = new ArrayList<String>();
		test.add("testuser1");
		test.add("testuser2");
		sampleUserRepository.findAllByUsername(test);
		
		ArrayList<SampleUser> test1 = new ArrayList<SampleUser>();
		SampleUser su1 = new SampleUser();
		su1.setAge(15);
		su1.setUsername("testuser1");
		test1.add(su1);
		
		su1 = new SampleUser();
		su1.setAge(20);
		su1.setUsername("testuser2");
		test1.add(su1);
		
		sampleUserRepository.findAll();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<SampleUser> cq = cb.createQuery(SampleUser.class);

//		entityManager.getTransaction().begin();
		Root<SampleUser> from = cq.from(SampleUser.class);
		cq.where(cb.equal(from.get("Id"), id));
		

		List<SampleUser> students = entityManager.createQuery(cq).getResultList();
				
		entityManager
			.createQuery ("select s from SampleUser s where s.id = :id ", SampleUser.class)
			.setParameter("id", id)
			.getResultList();
		
		return students;
		
	}


	
}
