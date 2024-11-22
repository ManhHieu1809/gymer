package com.projectgym.dto;

import com.projectgym.Entity.Orders;
import com.projectgym.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    private int orderID;
    private LocalDateTime orderDate;
    private Orders.statuss statuss;
    private double totalPrice;
    private UserDTO user;
    private List<OrderDetailDTO> orderDetails;



    public OrdersDTO(Orders order) {
        this.orderID = order.getOrderID();
        this.orderDate = order.getOrderDate();
        this.statuss = order.getStatuss();
        this.totalPrice = order.getTotalPrice();
        this.user = new UserDTO(order.getUser());
        this.orderDetails = order.getOrderDetails().stream().map(OrderDetailDTO::new).toList();
    }

}
