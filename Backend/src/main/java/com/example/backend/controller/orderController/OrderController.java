package com.example.backend.controller.orderController;

import com.example.backend.dto.Order.OrderRequest;
import com.example.backend.dto.Order.OrderResponse;
import com.example.backend.entities.Order.Order;
import com.example.backend.entities.Order.OrderDetail;
import com.example.backend.entities.Order.Payment;
import com.example.backend.entities.Order.TinhTrang;
import com.example.backend.repository.orderRepository.OrderRepository;
import com.example.backend.repository.orderRepository.TinhTrangRepository;
import com.example.backend.services.orderService.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TinhTrangRepository tinhTrangRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/place")
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody OrderRequest orderRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Order order = orderRequest.getOrder();
            List<OrderDetail> orderDetails = orderRequest.getOrderDetails();
            Payment payment = orderRequest.getPayment();

            if (order == null || orderDetails == null || orderDetails.isEmpty() || payment == null) {
                response.put("status", "error");
                response.put("message", "Invalid order request");
                return ResponseEntity.badRequest().body(response);
            }

            orderService.placeOrder(order, orderDetails, payment);
            response.put("status", "success");
            response.put("message", "Order placed successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while placing the order: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<OrderResponse> searchOrder(@RequestParam String orderCode) {
        Order order = orderService.findByOrderCode(orderCode);
        if (order != null) {
            OrderResponse orderResponse = orderService.getOrderDetails(order);
            return ResponseEntity.ok(orderResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the order: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable int id, @RequestBody Order newOrderData) {
        try {
            Optional<Order> existingOrderOpt = orderRepository.findById(id);
            if (existingOrderOpt.isPresent()) {
                Order existingOrder = existingOrderOpt.get();
                TinhTrang newTinhTrang = tinhTrangRepository.findById(newOrderData.getTinhTrang().getIdTinhTrang())
                        .orElseThrow(() -> new IllegalArgumentException("Tình trạng không tồn tại."));
                existingOrder.setTinhTrang(newTinhTrang);
                orderService.updateOrder(existingOrder);
                return ResponseEntity.ok(existingOrder);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/findByCustomer/{idKh}")
    public ResponseEntity<List<OrderResponse>> findOrdersByCustomerId(@PathVariable int idKh) {
        try {
            List<OrderResponse> orders = orderService.findOrderByIdUser(idKh);

            if (orders.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(orders);

        } catch (Exception e) {
            // Log lỗi (nếu cần)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
