package com.projectgym.dto;

import com.projectgym.Entity.OrderDetail;
import com.projectgym.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private int orderDetailID;
    private int quantity;
    private ProductDTO product;


    public OrderDetailDTO(OrderDetail orderDetail) {
        this.orderDetailID = orderDetail.getOrderDetailID();
        this.quantity = orderDetail.getQuantity();
        this.product = new ProductDTO(orderDetail.getProduct());
    }
}
