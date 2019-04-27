package com.example.template;


import java.io.Serializable;

public class DeliveryPretended  implements Serializable {


    private Delivery delivery;
    private String type;

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
