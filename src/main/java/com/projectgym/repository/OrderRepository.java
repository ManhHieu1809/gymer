package com.projectgym.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projectgym.Entity.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser_UserID(Long userId);
    // Lấy doanh thu theo ngày (filter theo trạng thái "Delivered")
    @Query("SELECT SUM(o.totalPrice) FROM Orders o WHERE o.statuss = 'Delivered' AND o.orderDate BETWEEN :startDate AND :endDate")
    Double getRevenueByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Lấy tổng doanh thu
    @Query("SELECT SUM(o.totalPrice) FROM Orders o WHERE o.statuss = 'Delivered'")
    Double getTotalRevenue();
    // Lấy doanh thu theo tháng (filter theo trạng thái "Delivered")
    @Query("SELECT SUM(o.totalPrice) FROM Orders o WHERE o.statuss = 'Delivered' AND FUNCTION('MONTH', o.orderDate) = :month AND FUNCTION('YEAR', o.orderDate) = :year")
    Double getRevenueByMonth(@Param("month") int month, @Param("year") int year);
}
