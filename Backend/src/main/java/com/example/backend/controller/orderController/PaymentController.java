package com.example.backend.controller.orderController;

import com.example.backend.dto.Order.OrderRequest;
import com.example.backend.entities.Order.Order;
import com.example.backend.entities.Order.OrderDetail;
import com.example.backend.entities.Order.Payment;
import com.example.backend.entities.Order.TinhTrang;
import com.example.backend.repository.orderRepository.OrderRepository;
import com.example.backend.repository.orderRepository.TinhTrangRepository;
import com.example.backend.services.orderService.OrderService;
import com.example.backend.services.orderService.TransactionPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pay")
public class PaymentController {
    @Autowired
    private TinhTrangRepository tinhTrangRepository;

    @Autowired
    private TransactionPayment transactionPayment;

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create-payment-link")
    public ResponseEntity<Map<String, String>> createPaymentLink(@RequestBody OrderRequest orderRequest) throws Exception {
        // Tạo liên kết thanh toán với thông tin đơn hàng tạm thời
        String domain = "http://localhost:8080";
        String productName = orderRequest.getOrderDetails().stream()
                .filter(detail -> detail.getTuyChonSanPham() != null && detail.getTuyChonSanPham().getProduct() != null)
                .map(detail -> detail.getTuyChonSanPham().getProduct().getTenSp())
                .reduce((p1, p2) -> p1 + ", " + p2).orElse("Sản phẩm");
        int quantity = orderRequest.getOrderDetails().stream().mapToInt(detail -> detail.getSoLuongMua()).sum();
        int amount = orderRequest.getOrder().getTongGia().intValue();

        String checkoutUrl = transactionPayment.createPaymentLink(domain, productName, quantity, amount);

        Map<String, String> response = new HashMap<>();
        response.put("checkoutUrl", checkoutUrl);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/payment-return")
    public ResponseEntity<String> handlePaymentReturn(@RequestBody Map<String, Object> params) {
        try {
            String code = (String) params.get("code");
            String id = (String) params.get("id");
            boolean cancel = Boolean.parseBoolean(params.get("cancel").toString());
            String status = (String) params.get("status");
            String orderCode = (String) params.get("orderCode");

            // Log thông tin nhận được từ frontend
            System.out.println("Payment return received with code: " + code + ", id: " + id + ", cancel: " + cancel + ", status: " + status + ", orderCode: " + orderCode);

            if (params.get("order") == null) {
                return ResponseEntity.badRequest().body("Order data is null.");
            }
            if (params.get("orderDetails") == null) {
                return ResponseEntity.badRequest().body("Order details are null.");
            }
            if (params.get("payment") == null) {
                return ResponseEntity.badRequest().body("Payment data is null.");
            }

            Map<String, Object> orderData = (Map<String, Object>) params.get("order");
            List<Map<String, Object>> orderDetailsMap = (List<Map<String, Object>>) params.get("orderDetails");
            Map<String, Object> paymentData = (Map<String, Object>) params.get("payment");

            // Log thông tin chi tiết đơn hàng, chi tiết và thanh toán
            System.out.println("Order data: " + orderData);
            System.out.println("Order details: " + orderDetailsMap);
            System.out.println("Payment data: " + paymentData);

            if (cancel) {
                return ResponseEntity.badRequest().body("Payment was cancelled.");
            }

            if (!"00".equals(code)) {
                return ResponseEntity.badRequest().body("Payment failed: Invalid payment response code.");
            }

            if (!"PAID".equals(status)) {
                return ResponseEntity.badRequest().body("Payment not successful or pending.");
            }

            // Tạo và lưu đơn hàng
            Order order = new Order();
            order.setMaDonHang((String) orderData.get("maDonHang"));
            order.setIdKh((Integer) orderData.get("idKh"));
            order.setNgayLap(new Date());
            order.setTongGia(new BigDecimal(orderData.get("tongGia").toString()));
            order.setPhiShip(new BigDecimal(orderData.get("phiShip").toString()));
            order.setIdVoucher((Integer) orderData.get("idVoucher"));
            order.setTenNguoiNhan((String) orderData.get("tenNguoiNhan"));
            order.setDiaChi((String) orderData.get("diaChi"));
            order.setTinhThanh((String) orderData.get("tinhThanh"));
            order.setQuanHuyen((String) orderData.get("quanHuyen"));
            order.setPhuongXa((String) orderData.get("phuongXa"));
            order.setSdtKh((String) orderData.get("sdtKh"));

            // Chuyển đổi orderDetailsMap sang List<OrderDetail>
            List<OrderDetail> orderDetails = orderDetailsMap.stream().map(detailMap -> {
                OrderDetail detail = new OrderDetail();
                detail.setIdTuyChon((Integer) detailMap.get("idTuyChon"));
                detail.setSoLuongMua((Integer) detailMap.get("soLuongMua"));
                detail.setDonGia(new BigDecimal(detailMap.get("donGia").toString()));
                return detail;
            }).collect(Collectors.toList());

            // Chuyển đổi paymentData sang Payment
            Payment payment = new Payment();
            payment.setPhuongThuc((String) paymentData.get("phuongThuc"));
            payment.setSoTien(new BigDecimal(paymentData.get("soTien").toString()));

            // Lưu và xử lý đơn hàng khi thanh toán thành công
            orderService.placeOrder(order, orderDetails, payment);

            return ResponseEntity.ok("Payment successful, order saved.");
        } catch (Exception e) {
            System.out.println("Error processing payment return: " + e.getMessage());
            return ResponseEntity.status(500).body("Error processing payment: " + e.getMessage());
        }
    }
}
