package com.projectgym.service;

import com.projectgym.Entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetailsByOrderId(int orderId);
}
