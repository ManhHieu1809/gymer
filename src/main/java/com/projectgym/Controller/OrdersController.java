package com.projectgym.Controller;

import com.projectgym.Entity.Orders;
import com.projectgym.dto.OrdersDTO;
import com.projectgym.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/home/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    // Tạo đơn hàng
    @PostMapping
    public OrdersDTO createOrder(@RequestBody OrdersDTO ordersDTO) {
        return ordersService.createOrder(ordersDTO);
    }

    // Cập nhật trạng thái đơn hàng
    @PutMapping("/{id}/status")
    public OrdersDTO updateOrderStatus(@PathVariable Long id, @RequestParam Orders.statuss newStatus) {
        return ordersService.updateOrderStatus(id, newStatus);
    }

    // Xóa đơn hàng
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        ordersService.deleteOrder(id);
    }

    // Lấy thông tin đơn hàng theo ID
    @GetMapping("/{id}")
    public OrdersDTO getOrderById(@PathVariable Long id) {
        return ordersService.getOrderById(id);
    }

    // Lấy danh sách tất cả đơn hàng
    @GetMapping
    public List<OrdersDTO> getAllOrders() {
        return ordersService.getAllOrders();
    }

    // Lọc danh sách đơn hàng theo trạng thái
    @GetMapping("/status")
    public List<OrdersDTO> getOrdersByStatus(@RequestParam Orders.statuss status) {
        return ordersService.getOrdersByStatus(status);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrdersDTO>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrdersDTO> orders = ordersService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // API xem doanh thu trong khoảng thời gian
    @GetMapping("/revenue")
    public ResponseEntity<Double> getRevenue(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        Double revenue = ordersService.getRevenueByDateRange(startDate, endDate);
        return new ResponseEntity<>(revenue, HttpStatus.OK);
    }

    // API xem tổng doanh thu
    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        Double totalRevenue = ordersService.getTotalRevenue();
        return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
    }

    @GetMapping("/revenue/month")
    public ResponseEntity<Double> getRevenueByMonth(@RequestParam int month, @RequestParam int year) {
        Double revenue = ordersService.getRevenueByMonth(month, year);
        return new ResponseEntity<>(revenue, HttpStatus.OK);
    }
}