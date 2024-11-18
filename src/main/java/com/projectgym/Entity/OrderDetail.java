package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orderdetail")
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detailID")
    private int OrderDetailID;
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "orderID",nullable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "productID",nullable = false)
    private Product product;
}
