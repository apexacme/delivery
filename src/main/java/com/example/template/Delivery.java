package com.example.template;



import com.example.template.example.SampleUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import java.io.Serializable;

@Entity
public class Delivery implements Serializable {

    @Id @GeneratedValue
    String deliveryID;
    String deliveryAddress;
    String deliveryState;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @PostPersist
    private void publishProductRegistered() {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        KafkaTemplate kafkaTemplate = Application.applicationContext.getBean(KafkaTemplate.class);

        DeliveryPretended deliveryPretended = new DeliveryPretended();
        try {
            deliveryPretended.getClass();

            json = objectMapper.writeValueAsString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        ProducerRecord producerRecord = new ProducerRecord<>("topic", json);
        kafkaTemplate.send(producerRecord);
    }
//    public Delivery(){
//
//    }
//    public Delivery(String deliveryID, String deliveryAddress, String deliveryState) {
//        this.deliveryID = deliveryID;
//        this.deliveryAddress = deliveryAddress;
//        this.deliveryState = deliveryState;
//    }




    public String getDeliveryID() {
        return deliveryID;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
