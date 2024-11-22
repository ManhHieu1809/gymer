package com.projectgym.service;

import com.projectgym.Entity.Orders;
import com.projectgym.dto.OrdersDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrdersService {
    OrdersDTO createOrder(OrdersDTO ordersDTO);
    OrdersDTO updateOrderStatus(Long orderId, Orders.statuss newStatus);
    List<OrdersDTO> getAllOrders();
    OrdersDTO getOrderById(Long orderId);
    void deleteOrder(Long orderId);
    List<OrdersDTO> getOrdersByStatus(Orders.statuss status);
    List<OrdersDTO> getOrdersByUserId (Long userId);

    Double getRevenueByDateRange(LocalDate startDate, LocalDate endDate);
    Double getTotalRevenue();
    Double getRevenueByMonth(int month, int year);
}
