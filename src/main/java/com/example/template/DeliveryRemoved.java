package com.example.template;

public class DeliveryRemoved {
    private Long id;
    private String type = this.getClass().getSimpleName();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
