package com.example.backend.services.orderService;

import com.example.backend.dto.Order.OrderItemResponse;
import com.example.backend.dto.Order.OrderResponse;
import com.example.backend.entities.Order.Order;
import com.example.backend.entities.Order.OrderDetail;
import com.example.backend.entities.Order.Payment;
import com.example.backend.entities.Order.TinhTrang;
import com.example.backend.entities.cart.CartItem;
import com.example.backend.entities.product.ProductOption;
import com.example.backend.entities.user.Role;
import com.example.backend.entities.user.User;
import com.example.backend.entities.voucher.Voucher;
import com.example.backend.repository.cartRepository.CartRepository;
import com.example.backend.repository.orderRepository.OrderDetailRepository;
import com.example.backend.repository.orderRepository.OrderRepository;
import com.example.backend.repository.orderRepository.PaymentRepository;
import com.example.backend.repository.orderRepository.TinhTrangRepository;
import com.example.backend.repository.productRepository.ProductOptionRepository;
import com.example.backend.repository.userRepository.UserRepository;
import com.example.backend.repository.voucherRepository.VoucherRepository;
import com.example.backend.services.emailService.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private int counter = 1;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TinhTrangRepository tinhTrangRepository;

    @Autowired
    private UserRepository userRepository;

    private Voucher voucher;

    private final EmailService emailService;


    @Autowired
    private VoucherRepository voucherRepository;

    public Order saveTemporaryOrder(Order order, List<OrderDetail> orderDetails, Payment payment) {
        // Truy vấn trạng thái đơn hàng "Pending" từ cơ sở dữ liệu
        TinhTrang pendingStatus = tinhTrangRepository.findByTinhTrang("Pending")
                .orElseThrow(() -> new IllegalArgumentException("Trạng thái đơn hàng 'Pending' không tồn tại."));

        // Đặt trạng thái đơn hàng là "Pending"
        order.setTinhTrang(pendingStatus);

        // Lưu thông tin đơn hàng tạm thời
        Order savedOrder = orderRepository.save(order);
        for (OrderDetail detail : orderDetails) {
            detail.setIdDdh(savedOrder.getIdDdh());
            orderDetailRepository.save(detail);
        }
        payment.setIdDdh(savedOrder.getIdDdh());
        paymentRepository.save(payment);
        return savedOrder;
    }

    public void finalizeOrder(Order order, List<OrderDetail> orderDetails, Payment payment) {
        // Truy vấn trạng thái đơn hàng "Processing" từ cơ sở dữ liệu
        TinhTrang processingStatus = tinhTrangRepository.findByTinhTrang("Processing")
                .orElseThrow(() -> new IllegalArgumentException("Trạng thái đơn hàng 'Processing' không tồn tại."));

        // Cập nhật trạng thái đơn hàng là "Processing"
        order.setTinhTrang(processingStatus);

        // Thực hiện các bước cần thiết để lưu đơn hàng và chi tiết đơn hàng vào cơ sở dữ liệu khi thanh toán thành công
        orderRepository.save(order);
        for (OrderDetail detail : orderDetails) {
            detail.setIdDdh(order.getIdDdh());
            orderDetailRepository.save(detail);
        }
        payment.setIdDdh(order.getIdDdh());
        paymentRepository.save(payment);
    }


    @Transactional
    @Override
    public void placeOrder(Order order, List<OrderDetail> orderDetails, Payment payment) {
        // Tạo mã đơn hàng duy nhất
        order.setMaDonHang(generateUniqueOrderCode());

        // Thiết lập trạng thái đơn hàng thành "Pending"
        TinhTrang pendingStatus = tinhTrangRepository.findByTinhTrang("Pending")
                .orElseThrow(() -> new IllegalArgumentException("Trạng thái đơn hàng 'Pending' không tồn tại."));
        order.setTinhTrang(pendingStatus);

        // Lưu đơn hàng
        Order savedOrder = orderRepository.save(order);

        // Đặt idTinhTrang sau khi order đã được lưu
        order.setIdTinhTrang(pendingStatus.getIdTinhTrang());
        orderRepository.save(order);

        // Lưu chi tiết đơn hàng và cập nhật số lượng sản phẩm
        for (OrderDetail detail : orderDetails) {
            detail.setIdDdh(savedOrder.getIdDdh());
            ProductOption productOption = productOptionRepository.findById(detail.getIdTuyChon())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid idTuyChon: " + detail.getIdTuyChon()));

            // Cập nhật số lượng sản phẩm
            updateProductQuantity(productOption, detail.getSoLuongMua());

            // Xóa sản phẩm trong giỏ hàng
            removeCartItem(order.getIdKh(), detail.getIdTuyChon());

            // Lưu chi tiết đơn hàng
            orderDetailRepository.save(detail);
        }

        // Lưu thông tin thanh toán
        payment.setIdDdh(savedOrder.getIdDdh());
        paymentRepository.save(payment);

        // Giảm số lượng mã giảm giá nếu có mã giảm giá
        if (order.getIdVoucher() != null) {
            Voucher voucher = voucherRepository.findById(order.getIdVoucher())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid idVoucher: " + order.getIdVoucher()));

            if (voucher.getSoLuong() > 0) {
                voucher.setSoLuong(voucher.getSoLuong() - 1);
                voucherRepository.save(voucher);
            } else {
                throw new IllegalArgumentException("Voucher has no remaining quantity.");
            }
        }

        // Gửi email cho tất cả Admin
        sendEmailToAdmins(savedOrder);
    }


    @Transactional
    public void updateOrder(Order order) {
        Optional<Order> existingOrderOpt = orderRepository.findById(order.getIdDdh());
        if (existingOrderOpt.isPresent()) {
            Order existingOrder = existingOrderOpt.get();
            TinhTrang newTinhTrang = order.getTinhTrang();

            // Kiểm tra nếu trạng thái mới là "Cancel" hoặc "Return"
            if (newTinhTrang.getTinhTrang().equals("Cancel") || newTinhTrang.getTinhTrang().equals("Return")) {
                // Tăng lại số lượng sản phẩm
                List<OrderDetail> orderDetails = orderDetailRepository.findByIdDdh(existingOrder.getIdDdh());
                for (OrderDetail detail : orderDetails) {
                    ProductOption productOption = productOptionRepository.findById(detail.getIdTuyChon())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid idTuyChon: " + detail.getIdTuyChon()));
                    productOption.setSoLuong(productOption.getSoLuong() + detail.getSoLuongMua());
                    productOptionRepository.save(productOption);
                }
            }

            // Cập nhật trạng thái đơn hàng
            existingOrder.setTinhTrang(newTinhTrang);
            orderRepository.save(existingOrder);
        } else {
            throw new IllegalArgumentException("Đơn hàng không tồn tại.");
        }
    }

    private void sendEmailToAdmins(Order order) {
        List<User> admins = userRepository.findAllByRole(Role.ADMIN);
        String subject = "Bạn có đơn hàng mới!";
        String content = order.toString();

        for (User admin : admins) {
            try {
                emailService.sendEmail(admin.getMail(), subject, content, null);
            } catch (Exception e) {
                // Handle exception
                e.printStackTrace();
            }
        }
    }
    private void updateProductQuantity(ProductOption productOption, int quantityPurchased) {
        int newQuantity = productOption.getSoLuong() - quantityPurchased;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Số lượng không đủ cho sản phẩm: " + productOption.getIdTuyChon());
        }
        productOption.setSoLuong(newQuantity);
        productOptionRepository.save(productOption);
    }

    private void removeCartItem(int userId, int productOptionId) {
        CartItem cartItem = cartRepository.findByUserIdKhAndProductOptionIdTuyChon(userId, productOptionId);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
        }
    }

    private String generateUniqueOrderCode() {
        String prefix = "DH";
        String datePart = new SimpleDateFormat("yyyyMMdd").format(new Date()); // Định dạng ngày YYYYMMDD
        String formattedCounter;

        synchronized (this) { // Đảm bảo an toàn trong môi trường đa luồng
            formattedCounter = String.format("%05d", counter);
            counter++; // Tự động tăng sau mỗi lần gọi
        }

        return prefix + "-" + datePart + "-" + formattedCounter;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public Order findByOrderCode(String orderCode) {
        return orderRepository.findByMaDonHang(orderCode).orElse(null);
    }




    @Override
    public OrderResponse getOrderDetails(Order order) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByIdDdh(order.getIdDdh());

        List<OrderItemResponse> items = orderDetails.stream()
                .map(detail -> {
                    OrderItemResponse item = new OrderItemResponse();
                    item.setTenSanPham(detail.getTuyChonSanPham().getProduct().getTenSp());
                    item.setSoLuong(detail.getSoLuongMua());
                    item.setDonGia(detail.getDonGia());
                    item.setMauSac(detail.getTuyChonSanPham().getMauSac());
                    item.setDungLuong(detail.getTuyChonSanPham().getDungLuong());
                    item.setLinkMauAnhSP(detail.getTuyChonSanPham().getLinkMauAnhSp());
                    return item;
                }).collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setIdDdh(order.getIdDdh());
        response.setMaDonHang(order.getMaDonHang());
        response.setTenKhachHang(order.getKhachHang() != null ? order.getKhachHang().getTenKh() : null);
        response.setItems(items);
        response.setTongGia(order.getTongGia());
        response.setPhiShip(order.getPhiShip());
        response.setTinhTrang(getTinhTrangResponse(order));
        response.setIdVoucher(order.getIdVoucher());
        response.setTenNguoiNhan(order.getTenNguoiNhan());
        response.setDiaChi(order.getDiaChi());
        response.setTinhThanh(order.getTinhThanh());
        response.setQuanHuyen(order.getQuanHuyen());
        response.setPhuongXa(order.getPhuongXa());
        response.setSdtKh(order.getSdtKh());
        response.setPayment(getPaymentResponse(order));

        return response;
    }

    private OrderResponse.TinhTrangResponse getTinhTrangResponse(Order order) {
        if (order.getTinhTrang() != null) {
            OrderResponse.TinhTrangResponse tinhTrangResponse = new OrderResponse.TinhTrangResponse();
            tinhTrangResponse.setIdTinhTrang(order.getTinhTrang().getIdTinhTrang());
            tinhTrangResponse.setTinhTrang(order.getTinhTrang().getTinhTrang());
            return tinhTrangResponse;
        }
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderResponse convertToDTO(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.setIdDdh(order.getIdDdh());
        dto.setIdKh(order.getIdKh());
        dto.setMaDonHang(order.getMaDonHang());
        dto.setTenKhachHang(order.getKhachHang() != null ? order.getKhachHang().getTenKh() : null);
        dto.setTongGia(order.getTongGia());
        dto.setPhiShip(order.getPhiShip());
        dto.setNgayLap(order.getNgayLap());
        dto.setTinhTrang(getTinhTrangResponse(order));
        dto.setIdVoucher(order.getIdVoucher());
        dto.setTenNguoiNhan(order.getTenNguoiNhan());
        dto.setDiaChi(order.getDiaChi());
        dto.setTinhThanh(order.getTinhThanh());
        dto.setQuanHuyen(order.getQuanHuyen());
        dto.setPhuongXa(order.getPhuongXa());
        dto.setSdtKh(order.getSdtKh());
        dto.setItems(getOrderItems(order));
        dto.setPayment(getPaymentResponse(order));
        return dto;
    }






    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }




    private List<OrderItemResponse> getOrderItems(Order order) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByIdDdh(order.getIdDdh());
        return orderDetails.stream()
                .map(detail -> {
                    OrderItemResponse item = new OrderItemResponse();
                    item.setTenSanPham(detail.getTuyChonSanPham().getProduct().getTenSp());
                    item.setSoLuong(detail.getSoLuongMua());
                    item.setDonGia(detail.getDonGia());
                    item.setMauSac(detail.getTuyChonSanPham().getMauSac());
                    item.setDungLuong(detail.getTuyChonSanPham().getDungLuong());
                    item.setLinkMauAnhSP(detail.getTuyChonSanPham().getLinkMauAnhSp());
                    return item;
                }).collect(Collectors.toList());
    }

    private OrderResponse.PaymentResponse getPaymentResponse(Order order) {
        Payment payment = paymentRepository.findByIdDdh(order.getIdDdh());
        if (payment != null) {
            OrderResponse.PaymentResponse paymentResponse = new OrderResponse.PaymentResponse();
            paymentResponse.setPhuongThuc(payment.getPhuongThuc());
            paymentResponse.setSoTien(payment.getSoTien());
            return paymentResponse;
        }
        return null;
    }


    public List<OrderResponse> findOrderByIdUser(int idKh) {
        List<Order> orders = orderRepository.findByIdKh(idKh);

        // Nếu danh sách rỗng, trả về danh sách rỗng thay vì null
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
