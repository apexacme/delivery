package com.example.template;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Delivery implements Serializable {

    @Id @GeneratedValue
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

    public Long getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(Long deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeveryState() {
        return deveryState;
    }

    public void setDeveryState(String deveryState) {
        this.deveryState = deveryState;
    }
}
