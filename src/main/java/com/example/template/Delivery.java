package com.example.template;



import com.example.template.example.SampleUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Delivery implements Serializable {

    @GeneratedValue
    private Long deliveryID;
    private String deliveryAddress;
    private String deveryState;

    @PostPersist
    private void publishDeliveryPretended() {

        KafkaTemplate kafkaTemplate = Application.applicationContext.getBean(KafkaTemplate.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        DeliveryPretended deliveryPretended= new DeliveryPretended();
        try {
//            deliveryPretended.setCampaign(this);
//            campaignRegistered.setType(CampaignRegistered.class.getSimpleName());
            json = objectMapper.writeValueAsString(deliveryPretended);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        ProducerRecord producerRecord = new ProducerRecord<>("topic", json);
        kafkaTemplate.send(producerRecord);
    }

    @PostRemove
    private void publishDeliveryCancelled() {

        KafkaTemplate kafkaTemplate = Application.applicationContext.getBean(KafkaTemplate.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        DeliveryCancelled deliveryCancelled= new DeliveryCancelled();
        try {
//            campaignRegistered.setCampaign(this);
//            campaignRegistered.setType(CampaignRegistered.class.getSimpleName());
            json = objectMapper.writeValueAsString(deliveryCancelled);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        ProducerRecord producerRecord = new ProducerRecord<>("topic", json);
        kafkaTemplate.send(producerRecord);
    }
}
