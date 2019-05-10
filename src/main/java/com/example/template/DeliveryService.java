package com.example.template;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class DeliveryService {
    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = "mallTopic")
    public void onOrderPlaced(@Payload String message, ConsumerRecord<?, ?> consumerRecord) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OrderPlaced orderPlaced = null;
        String json = null;

        try {
            orderPlaced = objectMapper.readValue(message, OrderPlaced.class);
            if ( !OrderPlaced.class.getSimpleName().equals(orderPlaced.getType()) ) {
                System.out.println("subscriber : " + message);
                return;
            }

            Delivery delivery = new Delivery();
            delivery.setOrderId(orderPlaced.getId().toString());

            deliveryRepository.save(delivery);

            // 5초간 sleep 한 후, 배송완료로 상태 변경.
            try {
                Thread.sleep(5000);
            } catch(InterruptedException ie) {
                ie.printStackTrace();
        }

        DeliveryCompleted  deliveryCompleted = new DeliveryCompleted();
        deliveryCompleted.setId(orderPlaced.getId());
        deliveryCompleted.setOrderId(orderPlaced.getId().toString());

        json = objectMapper.writeValueAsString(deliveryCompleted);
        ProducerRecord producerRecord = new ProducerRecord<>("mallTopic", json);
        kafkaTemplate.send(producerRecord);


//            Optional<Delivery> delivery = orderRepository.findById(deliveryCompleted.getOrderId());
//            order.ifPresent(orderEntity -> orderEntity.setStatus(1));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
