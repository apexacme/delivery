package com.example.template;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "item")
public class Item  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long itemId;

    // 상품명
    String name;
    // 상품가격
    int price;
    // 재고수량
    Stock stockCount;
    // 설명
    String desc;
    // 상품상태
    String status;
    // 추천 Id
    Long recommendId;


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Stock getStockCount() {
        return stockCount;
    }

    public void setStockCount(Stock stockCount) {
        this.stockCount = stockCount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }
}
