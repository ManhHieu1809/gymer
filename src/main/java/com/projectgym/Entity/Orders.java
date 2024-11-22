package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderID")
    private int orderID;
    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    @Column(name = "statuss")
    private statuss statuss;
    private double totalPrice;

    public enum statuss {
        Pending, Shipped, Delivered, Cancelled
    }

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Orders() {
        this.orderDetails = new ArrayList<>(); // Khởi tạo danh sách trong constructor
    }

}
