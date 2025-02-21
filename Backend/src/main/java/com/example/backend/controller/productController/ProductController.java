package com.example.backend.controller.productController;

import com.example.backend.dto.Product.ProductDTO;
import com.example.backend.entities.product.Product;
import com.example.backend.entities.product.ProductCategory;
import com.example.backend.entities.product.ProductOption;
import com.example.backend.services.productServices.DeleteProductResponse;
import com.example.backend.services.productServices.productService;
import com.example.backend.services.productServices.productServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final productService productService;

    @GetMapping("/latest")
    public List<Product> getLatestProducts() {
        return productService.getLatestProducts(10);
    }

    @GetMapping("/all")
    public List<ProductDTO> getAllProducts()
    {
        return productService.getAllProducts();
    }


    @GetMapping("/categories")
    public List<ProductCategory> getAllProductCategories()
    { return productService.getAllCategories(); }

    @PostMapping("/add/category")
    public ResponseEntity<?> createCategory(@RequestBody ProductCategory productCategory) {
        try {
            // Gọi phương thức createCategory trong service
            ProductCategory createdCategory = productService.createCategory(productCategory);
            return ResponseEntity.ok(createdCategory); // Trả về danh mục vừa tạo
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi nếu danh mục đã tồn tại
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi khác
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PutMapping("/category/update/{idDm}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Integer idDm,
            @RequestBody ProductCategory updatedCategory) {
        try {
            // Gọi service để cập nhật danh mục
            ProductCategory editedCategory = productService.editCategory(idDm, updatedCategory);
            return ResponseEntity.ok(editedCategory);
        } catch (IllegalArgumentException e) {
            // Trả về lỗi nếu danh mục không tồn tại
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Trả về lỗi chung nếu có lỗi khác
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the category");
        }
    }


    @DeleteMapping("/category/del/{idDm}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer idDm) {
        try {
            productService.deleteCategory(idDm);
            return ResponseEntity.ok("Category deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/option")
    public List<ProductOption> getAllOptions(){
        return productService.getAllProductOptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id)
    {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("update/{idSp}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer idSp, @RequestBody Product updatedProduct) {
        Optional<Product> updated = productService.updateProduct(idSp, updatedProduct);

        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if product not found
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            DeleteProductResponse response = productService.deleteProduct(id);
            if (response.getOrderDetails() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getOrderDetails());
            }
            return ResponseEntity.ok(response.getProductOptions());
        } catch (productServiceImpl.ProductInOrderException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getOrderDetails());
        }
    }

    @DeleteMapping("/delete1/{id}")
    public ResponseEntity<?> deleteProduct1(@PathVariable Integer id) {
        try {
            productService.deleteProduct1(id);
            return ResponseEntity.noContent().build();
        } catch (productServiceImpl.ProductInOrderException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getOrderDetails());
        }
    }


//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
//        try {
//            productService.deleteProduct(id);
//            return ResponseEntity.noContent().build();
//        } catch (ProductInOrderException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getOrderDetail());
//        }
//    }

    @PostMapping("/option/create/{idSp}")
    public ResponseEntity<ProductOption> createProductOption(@PathVariable Integer idSp, @RequestBody ProductOption productOption) {
        ProductOption createdProductOption = productService.createProductOption(idSp, productOption);
        return ResponseEntity.ok(createdProductOption);
    }

    @PutMapping("/option/update/{id}")
    public ResponseEntity<ProductOption> updateProductOption(@PathVariable Integer id, @RequestBody ProductOption productOption)
    { ProductOption updatedProductOption = productService.updateProductOption(id, productOption);
    if (updatedProductOption != null)
    { return ResponseEntity.ok(updatedProductOption); }
    else { return ResponseEntity.notFound().build(); } }

    @DeleteMapping("/option/delete/{id}")
    public ResponseEntity<Void> deleteProductOption(@PathVariable Integer id)
    { productService.deleteProductOption(id);
    return ResponseEntity.noContent().build(); }

    @GetMapping("/option/{id}")
    public ResponseEntity<ProductOption> getProductOptionById(@PathVariable Integer id) {
        ProductOption productOption = productService.getProductOptionById(id);
        if (productOption != null) {
            return ResponseEntity.ok(productOption);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String keyword)
    { List<ProductDTO> products = productService.searchProducts(keyword); return ResponseEntity.ok(products); }

}
