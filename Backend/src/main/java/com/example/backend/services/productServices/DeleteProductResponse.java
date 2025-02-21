package com.example.backend.services.productServices;

import com.example.backend.entities.Order.OrderDetail;
import com.example.backend.entities.product.ProductOption;

import java.util.List;

public class DeleteProductResponse {
    private List<OrderDetail> orderDetails;
    private List<ProductOption> productOptions;

    public DeleteProductResponse(List<OrderDetail> orderDetails, List<ProductOption> productOptions) {
        this.orderDetails = orderDetails;
        this.productOptions = productOptions;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<ProductOption> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }
}
