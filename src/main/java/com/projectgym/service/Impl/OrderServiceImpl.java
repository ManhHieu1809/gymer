package com.projectgym.service.Impl;

import com.projectgym.Entity.OrderDetail;
import com.projectgym.Entity.Orders;
import com.projectgym.Entity.Product;
import com.projectgym.Entity.User;
import com.projectgym.dto.OrdersDTO;
import com.projectgym.dto.OrderDetailDTO;
import com.projectgym.repository.MyAppUserRepository;
import com.projectgym.repository.OrderRepository;
import com.projectgym.repository.ProductRepository;
import com.projectgym.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrdersService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MyAppUserRepository userRepository;

    @Override
    public OrdersDTO createOrder(OrdersDTO ordersDTO) {
        Orders order = new Orders();
        order.setOrderDate(ordersDTO.getOrderDate());
        order.setStatuss(Orders.statuss.Pending); // Trạng thái mặc định là PENDING
        User user = userRepository.findById((long) ordersDTO.getUser().getUserID())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        // Tính tổng giá trị đơn hàng và tạo OrderDetail
        double totalPrice = 0;
        for (OrderDetailDTO detailDTO : ordersDTO.getOrderDetails()) {
            OrderDetail detail = new OrderDetail();
            detail.setQuantity(detailDTO.getQuantity());
            Product product = productRepository.findById((long) detailDTO.getProduct().getProductID())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            detail.setProduct(product);
            detail.setOrders(order);

            totalPrice += detail.getProduct().getPrice() * detail.getQuantity();
            order.getOrderDetails().add(detail);
        }
        order.setTotalPrice(totalPrice);

        Orders savedOrder = orderRepository.save(order);
        return new OrdersDTO(savedOrder);
    }

    // Cập nhật trạng thái đơn hàng
    @Override
    public OrdersDTO updateOrderStatus(Long orderId, Orders.statuss newStatus) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        order.setStatuss(newStatus);
        Orders updatedOrder = orderRepository.save(order);
        return new OrdersDTO(updatedOrder);
    }

    // Xóa đơn hàng
    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }

    // Lấy thông tin đơn hàng theo ID
    @Override
    public OrdersDTO getOrderById(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return new OrdersDTO(order);
    }

    // Lấy danh sách tất cả đơn hàng
    @Override
    public List<OrdersDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrdersDTO::new)
                .collect(Collectors.toList());
    }

    // Lọc danh sách đơn hàng theo trạng thái
    @Override
    public List<OrdersDTO> getOrdersByStatus(Orders.statuss status) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getStatuss() == status)
                .map(OrdersDTO::new)
                .collect(Collectors.toList());
    }



    // Tìm kiếm đơn hàng theo userID
    @Override
    public List<OrdersDTO> getOrdersByUserId(Long userId) {
        // Lấy danh sách các đơn hàng của user từ repository
        List<Orders> ordersList = orderRepository.findByUser_UserID(userId);

        // Nếu không có đơn hàng nào, throw lỗi
        if (ordersList.isEmpty()) {
            throw new RuntimeException("No orders found for user with ID: " + userId);
        }

        // Chuyển đổi từ List<Orders> sang List<OrdersDTO> và trả về
        return ordersList.stream()
                .map(OrdersDTO::new)
                .collect(Collectors.toList());
    }


    // Lấy doanh thu trong khoảng thời gian
    @Override
    public Double getRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        // Chuyển đổi LocalDate thành LocalDateTime (đặt thời gian mặc định là 00:00:00)
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay().plusDays(1).minusNanos(1); // Đặt thời gian cuối ngày

        return orderRepository.getRevenueByDateRange(startDateTime, endDateTime);
    }

    // Lấy tổng doanh thu
    @Override
    public Double getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }
    // lấy tổng doanh thu theo tháng
    @Override
    public Double getRevenueByMonth(int month, int year) {
        return orderRepository.getRevenueByMonth(month, year);
    }
}
