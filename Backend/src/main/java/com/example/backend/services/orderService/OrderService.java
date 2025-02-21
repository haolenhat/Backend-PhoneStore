package com.example.backend.services.orderService;

import com.example.backend.dto.Order.OrderResponse;
import com.example.backend.entities.Order.Order;
import com.example.backend.entities.Order.OrderDetail;
import com.example.backend.entities.Order.Payment;
import com.example.backend.entities.product.ProductOption;

import java.util.List;

public interface OrderService {
    void placeOrder(Order order, List<OrderDetail> orderDetails, Payment payment);
    List<OrderResponse> getAllOrders();
    Order findByOrderCode(String orderCode);
    OrderResponse getOrderDetails(Order order);
    void deleteOrder(int id);
    void updateOrder(Order order);
    public List<OrderResponse> findOrderByIdUser(int idKh);
    Order saveTemporaryOrder(Order order, List<OrderDetail> orderDetails, Payment payment);
    void finalizeOrder(Order order, List<OrderDetail> orderDetails, Payment payment);
}
