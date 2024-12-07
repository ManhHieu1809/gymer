package com.projectgym.Controller;

import com.projectgym.Entity.Orders;
import com.projectgym.dto.OrdersDTO;
import com.projectgym.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    // API xem doanh thu theo tháng
    @GetMapping("/revenue/month")
    public ResponseEntity<Double> getRevenueByMonth(@RequestParam int month, @RequestParam int year) {
        Double revenue = ordersService.getRevenueByMonth(month, year);
        return new ResponseEntity<>(revenue, HttpStatus.OK);
    }

    // API tính phần trăm thay đổi doanh thu giữa tháng hiện tại và tháng trước
    @GetMapping("/revenue/change")
    public ResponseEntity<Double> getRevenueChangePercentage() {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        int lastMonth = currentMonth == 1 ? 12 : currentMonth - 1;  // Nếu là tháng 1, lấy tháng 12 của năm trước
        int lastYear = currentMonth == 1 ? currentYear - 1 : currentYear;

        // Lấy doanh thu của tháng hiện tại và tháng trước
        Double revenueCurrentMonth = ordersService.getRevenueByMonth(currentMonth, currentYear);
        Double revenueLastMonth = ordersService.getRevenueByMonth(lastMonth, lastYear);

        // Kiểm tra nếu doanh thu trả về là null, thay thế bằng 0.0
        revenueCurrentMonth = (revenueCurrentMonth != null) ? revenueCurrentMonth : 0.0;
        revenueLastMonth = (revenueLastMonth != null) ? revenueLastMonth : 0.0;

        if (revenueLastMonth == 0) {
            return ResponseEntity.ok(100.0); // Nếu tháng trước không có doanh thu, giả sử 100%
        }

        // Tính phần trăm thay đổi doanh thu
        double percentageChange = ((revenueCurrentMonth - revenueLastMonth) / revenueLastMonth) * 100;

        // Làm tròn kết quả phần trăm đến 2 chữ số thập phân
        BigDecimal roundedPercentage = new BigDecimal(percentageChange).setScale(2, RoundingMode.HALF_UP);

        return ResponseEntity.ok(roundedPercentage.doubleValue());
    }

}