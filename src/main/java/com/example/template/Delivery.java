package com.example.template;

import com.example.template.example.SampleUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Delivery {

    @Id @GeneratedValue
    private Long id;
    private String orderId;
    private String userId;
    private String addressTo;
    private String msg;
    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //            productRegistered.setName(this.getName());
    @PostPersist @PostUpdate
    private void publishDeliveryPrepared() {
        KafkaTemplate kafkaTemplate = Application.applicationContext.getBean(KafkaTemplate.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        DeliveryPrepared deliveryPrepared = new DeliveryPrepared();
        try {
            BeanUtils.copyProperties(this, deliveryPrepared);
//            deliveryPrepared.setId(this.getId());

            json = objectMapper.writeValueAsString(deliveryPrepared);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        ProducerRecord producerRecord = new ProducerRecord<>("mallTopic", json);
        kafkaTemplate.send(producerRecord);
    }

    @PostRemove
    private void publishDeliveryRemove() {
        KafkaTemplate kafkaTemplate = Application.applicationContext.getBean(KafkaTemplate.class);
        String json = null;
//        String msg = "Delivery Object Deleted..";
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        DeliveryRemoved deliveryRemoved = new DeliveryRemoved();
        deliveryRemoved.setId(this.getId());

        json = objectMapper.writeValueAsString(deliveryRemoved);
    } catch (JsonProcessingException e) {
        throw new RuntimeException("JSON format exception", e);
    }
        ProducerRecord producerRecord = new ProducerRecord<>("mallTopic", json);
        kafkaTemplate.send(producerRecord);
    }
}