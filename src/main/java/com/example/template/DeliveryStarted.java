package com.example.template;

import java.io.Serializable;

public class DeliveryStarted implements Serializable {

    String deliveryID; // orderID
    String deliveryAddress;
    String deliveryState;
    String type;

    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
