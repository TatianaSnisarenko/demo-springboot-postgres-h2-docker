package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "message")
@Data
public class Message implements Comparable<Message>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Message body is mandatory")
    private String body;


    @Override
    public int compareTo(Message o) {
        return Integer.compare(this.getId(), o.getId());
    }
}
