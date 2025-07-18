package com.example.learning_project.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn (name = "user_id")
    @JsonBackReference
    private User user;
}

