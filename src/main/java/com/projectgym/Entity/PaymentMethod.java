package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paymentmethod")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_methodID")
    private int paymentMethodID;
    @Column(name = "method_name")
    private String methodName;
    @Column(name = "details")
    private String details;

    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private User user;
}
