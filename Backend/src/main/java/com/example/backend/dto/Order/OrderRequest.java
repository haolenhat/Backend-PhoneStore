package com.example.backend.dto.Order;

import com.example.backend.entities.Order.Order;
import com.example.backend.entities.Order.OrderDetail;
import com.example.backend.entities.Order.Payment;
import java.util.List;

public class OrderRequest {
    private Order order;
    private List<OrderDetail> orderDetails;
    private Payment payment;

    // Getter và Setter

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
