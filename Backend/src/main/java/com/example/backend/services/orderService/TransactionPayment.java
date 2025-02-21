package com.example.backend.services.orderService;

import vn.payos.PayOS;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.CheckoutResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionPayment {
    private final PayOS payOS;

    public TransactionPayment(@Value("${payos.clientId}") String clientId,
                              @Value("${payos.apiKey}") String apiKey,
                              @Value("${payos.checksumKey}") String checksumKey) {
        this.payOS = new PayOS(clientId, apiKey, checksumKey);
    }

    public String createPaymentLink(String domain, String productName, int quantity, int amount) throws Exception {
        Long orderCode = System.currentTimeMillis() / 1000;
        ItemData itemData = ItemData
                .builder()
                .name(productName)
                .quantity(quantity)
                .price(amount)
                .build();

        PaymentData paymentData = PaymentData
                .builder()
                .orderCode(orderCode)
                .amount(amount)
                .description("Thanh toán đơn hàng")
                .returnUrl("http://localhost:4200/success")
                .cancelUrl("http://localhost:4200/cancel")
                .item(itemData)
                .build();

        CheckoutResponseData result = payOS.createPaymentLink(paymentData);
        return result.getCheckoutUrl();
    }
}
