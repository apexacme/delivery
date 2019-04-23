package com.example.template.example;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class SampleUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    int age;
    String username;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @PostPersist
    public void userPostUpdate(){
        System.out.println("Id = " + this.Id);
        System.out.println("age = " + this.age);
        System.out.println("username = " + this.username);
    }

}
