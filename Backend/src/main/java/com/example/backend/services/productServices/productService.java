package com.example.backend.services.productServices;

import com.example.backend.dto.Product.ProductDTO;
import com.example.backend.entities.product.Product;
import com.example.backend.entities.product.ProductCategory;
import com.example.backend.entities.product.ProductOption;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface productService {
    List<ProductCategory> getAllCategories();
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Integer id);
    List<ProductOption> getAllProductOptions();
    Product createProduct(Product product);
    Optional<Product> updateProduct(Integer idSp, Product updatedProduct);
    DeleteProductResponse deleteProduct(Integer id);
    ProductOption createProductOption( Integer idSp, ProductOption productOption);
    ProductOption updateProductOption(Integer id, ProductOption updatedProductOption);
    void deleteProductOption(Integer id);
    ProductOption getProductOptionById(Integer id);
    List<ProductDTO> searchProducts(String keyword);
    ProductCategory createCategory(ProductCategory productCategory);
    ProductCategory editCategory(Integer idDm, ProductCategory updatedCategory);
    void deleteCategory(Integer idDm);
    List<Product> getLatestProducts(int limit);
    void deleteProduct1(Integer id);
}
