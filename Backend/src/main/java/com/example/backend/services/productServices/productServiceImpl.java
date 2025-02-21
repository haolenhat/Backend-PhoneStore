package com.example.backend.services.productServices;

import com.example.backend.dto.Product.ProductCategoryDTO;
import com.example.backend.dto.Product.ProductDTO;
import com.example.backend.dto.Product.ProductOptionDTO;
import com.example.backend.entities.Order.OrderDetail;
import com.example.backend.entities.product.Product;
import com.example.backend.entities.product.ProductCategory;
import com.example.backend.entities.product.ProductOption;
import com.example.backend.repository.orderRepository.OrderDetailRepository;
import com.example.backend.repository.orderRepository.OrderRepository;
import com.example.backend.repository.productRepository.ProductCategoryRepository;
import com.example.backend.repository.productRepository.ProductOptionRepository;
import com.example.backend.repository.productRepository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class productServiceImpl implements productService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Override
    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setIdSp(product.getIdSp());
            productDTO.setTenSp(product.getTenSp());
            productDTO.setThongTinSp(product.getThongTinSp());
            productDTO.setLinkAnh(product.getLinkAnh());
            productDTO.setBaoHanh(product.getBaoHanh());
            productDTO.setBaoMat(product.getBaoMat());
            productDTO.setCamera(product.getCamera());
            productDTO.setBoNho(product.getBoNho());
            productDTO.setCongSac(product.getCongSac());
            productDTO.setHeDieuHanh(product.getHeDieuHanh());
            productDTO.setManHinh(product.getManHinh());
            productDTO.setTrongLuong(product.getTrongLuong());
            productDTO.setPin(product.getPin());
            productDTO.setTheSim(product.getTheSim());
            productDTO.setLinkAnh(product.getLinkAnh());
           productDTO.setSoLuotBinhLuan(product.getSoLuotBinhLuan());
           productDTO.setSoLuotDanhGia(product.getSoLuotDanhGia());
           productDTO.setSoSaoTrungBinh(product.getSoSaoTrungBinh());
            productDTO.setProductOptions(product.getProductOptions().stream().map(option -> {
                ProductOptionDTO optionDTO = new ProductOptionDTO();
                optionDTO.setIdTuyChon(option.getIdTuyChon());
                optionDTO.setMauSac(option.getMauSac());
                optionDTO.setLinkMauAnhSp(option.getLinkMauAnhSp());
                optionDTO.setDungLuong(option.getDungLuong());
                optionDTO.setGiaSp(option.getGiaSp());
                optionDTO.setKhuyenMai(option.getKhuyenMai());
                optionDTO.setSoLuong(option.getSoLuong());
                optionDTO.setGiaSauKhuyenMai(optionDTO.getGiaSauKhuyenMai());
                return optionDTO;
            }).collect(Collectors.toList()));

            ProductCategoryDTO categoryDTO = new ProductCategoryDTO(); categoryDTO.setIdDm(product.getProductCategory().getIdDm()); categoryDTO.setTenDm(product.getProductCategory().getTenDm()); productDTO.setProductCategory(categoryDTO);
            return productDTO;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Integer id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setIdSp(product.getIdSp());
            productDTO.setTenSp(product.getTenSp());
            productDTO.setThongTinSp(product.getThongTinSp());
            productDTO.setLinkAnh(product.getLinkAnh());
            productDTO.setBaoHanh(product.getBaoHanh());
            productDTO.setBaoMat(product.getBaoMat());
            productDTO.setCamera(product.getCamera());
            productDTO.setBoNho(product.getBoNho());
            productDTO.setCongSac(product.getCongSac());
            productDTO.setHeDieuHanh(product.getHeDieuHanh());
            productDTO.setManHinh(product.getManHinh());
            productDTO.setTrongLuong(product.getTrongLuong());
            productDTO.setPin(product.getPin());
            productDTO.setTheSim(product.getTheSim());
            productDTO.setSoLuotBinhLuan(product.getSoLuotBinhLuan());
            productDTO.setSoLuotDanhGia(product.getSoLuotDanhGia());
            productDTO.setSoSaoTrungBinh(product.getSoSaoTrungBinh());
            productDTO.setProductOptions(
                    product.getProductOptions()
                            .stream()
                            .map(option -> {
                                ProductOptionDTO optionDTO = new ProductOptionDTO();
                                optionDTO.setIdTuyChon(option.getIdTuyChon());
                                optionDTO.setMauSac(option.getMauSac());
                                optionDTO.setLinkMauAnhSp(option.getLinkMauAnhSp());
                                optionDTO.setDungLuong(option.getDungLuong());
                                optionDTO.setGiaSp(option.getGiaSp());
                                optionDTO.setKhuyenMai(option.getKhuyenMai());
                                optionDTO.setSoLuong(option.getSoLuong());
                                optionDTO.setGiaSauKhuyenMai(
                                        option.getGiaSp() * (1 - option.getKhuyenMai() / 100)
                                );
                                return optionDTO;
                            })
                            .collect(Collectors.toList())
            );
            ProductCategoryDTO categoryDTO = new ProductCategoryDTO(); categoryDTO.setIdDm(product.getProductCategory().getIdDm()); categoryDTO.setTenDm(product.getProductCategory().getTenDm()); productDTO.setProductCategory(categoryDTO);

            return productDTO;
        } else {
            return null;
        }
    }


    @Override
    public List<ProductOption> getAllProductOptions() {
        return productOptionRepository.findAll();
    }


    @Override
    @Transactional
    public Product createProduct(Product product) {
        ProductCategory productCategory = product.getProductCategory();
        ProductCategory existingCategory = productCategoryRepository.findByTenDm(productCategory.getTenDm());
        if (existingCategory != null) {
            product.setProductCategory(existingCategory);
        } else {
            productCategory = productCategoryRepository.save(productCategory);
            product.setProductCategory(productCategory);
        }

        // Thiết lập giá trị ngayTao trước khi lưu
        product.setNgayTao(Timestamp.from(Instant.now()));

        return productRepository.save(product);
    }

//    Hiển thị 10 sản phẩm mới nhất
    public List<Product> getLatestProducts(int limit) {
        return productRepository.findTop10ByOrderByNgayTaoDesc();
    }

    @Override
    @Transactional
    public  ProductCategory createCategory(ProductCategory productCategory){
        ProductCategory existingCategory = productCategoryRepository.findByTenDm(productCategory.getTenDm());
        if (existingCategory != null) {
            throw new IllegalArgumentException("Category already exists: " + productCategory.getTenDm());
        }

        return productCategoryRepository.save(productCategory);
    }


    @Transactional
    public ProductCategory editCategory(Integer idDm, ProductCategory updatedCategory) {
        // Tìm danh mục theo ID
        Optional<ProductCategory> existingCategoryOpt = productCategoryRepository.findByidDm(idDm);

        // Kiểm tra nếu danh mục tồn tại
        if (existingCategoryOpt.isPresent()) {
            ProductCategory existingCategory = existingCategoryOpt.get();

            // Cập nhật thông tin danh mục
            if (updatedCategory.getTenDm() != null) {
                existingCategory.setTenDm(updatedCategory.getTenDm());
            }

            // Nếu danh mục có danh sách sản phẩm, cập nhật danh sách sản phẩm
            if (updatedCategory.getProducts() != null) {
                existingCategory.getProducts().clear();
                existingCategory.getProducts().addAll(updatedCategory.getProducts());
                existingCategory.getProducts().forEach(product -> product.setProductCategory(existingCategory));
            }

            // Lưu thay đổi
            return productCategoryRepository.save(existingCategory);
        } else {
            // Ném ngoại lệ nếu không tìm thấy danh mục
            throw new IllegalArgumentException("Category with ID " + idDm + " does not exist.");
        }
    }

    @Transactional
    public void deleteCategory(Integer idDm) {
        ProductCategory existingCategory = productCategoryRepository.findById(idDm)
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + idDm + " does not exist."));

        // Kiểm tra xem danh mục có chứa sản phẩm nào không
        if (!existingCategory.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete category with ID " + idDm + " because it contains products.");
        }

        // Xóa danh mục
        productCategoryRepository.delete(existingCategory);
    }





    @Override
    @Transactional
    public Optional<Product> updateProduct(Integer idSp, Product updatedProduct) {
        if (updatedProduct == null) {
            return Optional.empty(); // Handle null updatedProduct
        }

        Product product = productRepository.findByIdSp(idSp).orElse(null);
        if (product != null) {
            product.setTenSp(updatedProduct.getTenSp());
            product.setThongTinSp(updatedProduct.getThongTinSp());
            product.setLinkAnh(updatedProduct.getLinkAnh());
            product.setTrongLuong(updatedProduct.getTrongLuong());
            product.setManHinh(updatedProduct.getManHinh());
            product.setBoNho(updatedProduct.getBoNho());
            product.setTheSim(updatedProduct.getTheSim());
            product.setHeDieuHanh(updatedProduct.getHeDieuHanh());
            product.setCongSac(updatedProduct.getCongSac());
            product.setCamera(updatedProduct.getCamera());
            product.setBaoMat(updatedProduct.getBaoMat());
            product.setPin(updatedProduct.getPin());
            product.setBaoHanh(updatedProduct.getBaoHanh());
            product.setSoLuotBinhLuan(updatedProduct.getSoLuotBinhLuan());
            product.setSoLuotDanhGia(updatedProduct.getSoLuotDanhGia());
            product.setSoSaoTrungBinh(updatedProduct.getSoSaoTrungBinh());

            // Update ProductCategory
            ProductCategory productCategory = updatedProduct.getProductCategory();
            if (productCategory != null) {
                ProductCategory existingCategory = productCategoryRepository.findByTenDm(productCategory.getTenDm());
                product.setProductCategory(existingCategory != null ? existingCategory : productCategoryRepository.save(productCategory));
            }

            // Update ProductOptions
            if (updatedProduct.getProductOptions() != null) {
                for (ProductOption updatedOption : updatedProduct.getProductOptions()) {
                    ProductOption existingOption = productOptionRepository.findById(updatedOption.getIdTuyChon()).orElse(null);
                    if (existingOption != null) {
                        existingOption.setMauSac(updatedOption.getMauSac());
                        existingOption.setLinkMauAnhSp(updatedOption.getLinkMauAnhSp());
                        existingOption.setDungLuong(updatedOption.getDungLuong());
                        existingOption.setGiaSp(updatedOption.getGiaSp());
                        existingOption.setKhuyenMai(updatedOption.getKhuyenMai());
                        existingOption.setSoLuong(updatedOption.getSoLuong());
                        // Optionally check for changes before saving
                        productOptionRepository.save(existingOption);
                    }
                }
            }

            return Optional.of(productRepository.save(product));
        }
        return Optional.empty(); // Return empty Optional if product not found
    }

    private  final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

//    @Override
//    @Transactional
//    public void deleteProduct(Integer id) {
//        // Kiểm tra xem sản phẩm có tồn tại trong đơn hàng nào không
//        List<OrderDetail> orderDetails = orderDetailRepository.findByTuyChonSanPham_Product_IdSp(id);
//        if (!orderDetails.isEmpty()) {
//            throw new ProductInOrderException("Cannot delete product with ID " + id + " because it is part of an order.", orderDetails);
//        }
//
//        // Xóa sản phẩm nếu không tồn tại trong đơn hàng nào
//        productRepository.deleteById(id);
//    }


//    @Override
//    @Transactional
//    public void deleteProduct(Integer id) {
//        // Kiểm tra xem sản phẩm có tồn tại trong đơn hàng nào không
//        List<OrderDetail> orderDetails = orderDetailRepository.findByTuyChonSanPham_Product_IdSp(id);
//        if (!orderDetails.isEmpty()) {
//            throw new ProductInOrderException("Cannot delete product with ID " + id + " because it is part of an order.", orderDetails);
//        }
//
//        productRepository.deleteById(id);
//    }




    @Override
    @Transactional
    public DeleteProductResponse deleteProduct(Integer id) {
        // Kiểm tra xem sản phẩm có tồn tại trong đơn hàng nào không
        List<OrderDetail> orderDetails = orderDetailRepository.findByTuyChonSanPham_Product_IdSp(id);
        if (!orderDetails.isEmpty()) {
            throw new ProductInOrderException("Cannot delete product with ID " + id + " because it is part of an order.", orderDetails);
        }

        // Lấy thông tin chi tiết của ProductOption trước khi xóa sản phẩm
        List<ProductOption> productOptions = productOptionRepository.findByProduct_IdSp(id);

        // Xóa sản phẩm nếu không tồn tại trong đơn hàng nào
        productRepository.deleteById(id);

        // Trả về thông tin chi tiết của ProductOption
        return new DeleteProductResponse(null, productOptions);
    }

    @Override
    @Transactional
    public  void deleteProduct1(Integer id) {
        productRepository.deleteById(id);
    }

    public class ProductInOrderException extends RuntimeException {
        private List<OrderDetail> orderDetails;

        public ProductInOrderException(String message, List<OrderDetail> orderDetails) {
            super(message);
            this.orderDetails = orderDetails;
        }

        public List<OrderDetail> getOrderDetails() {
            return orderDetails;
        }
    }

//    public void deleteProduct(Integer id) {
//        // Kiểm tra xem sản phẩm có tồn tại trong đơn hàng nào không
//        if (orderRepository.existsByProductId(id)) {
//            throw new IllegalArgumentException("Cannot delete product with ID " + id + " because it is part of an order.");
//        }
//
//        // Xóa sản phẩm nếu không tồn tại trong đơn hàng nào
//        productRepository.deleteById(id);
//    }



    @Transactional
    public ProductOption createProductOption( Integer idSp, ProductOption productOption) {
        // Tìm product và category theo id
        Optional<Product> productOptional = productRepository.findByIdSp(idSp);

        if (productOptional.isPresent()) {
            productOption.setProduct(productOptional.get());
            return productOptionRepository.save(productOption);
        } else {
            throw new EntityNotFoundException("Product or Category not found");
        }
    }


    @Transactional
    public ProductOption updateProductOption(Integer id, ProductOption updatedProductOption) {
        ProductOption productOption = productOptionRepository.findById(id).orElse(null);
        if (productOption != null) {
            productOption.setMauSac(updatedProductOption.getMauSac());
            productOption.setLinkMauAnhSp(updatedProductOption.getLinkMauAnhSp());
            productOption.setDungLuong(updatedProductOption.getDungLuong());
            productOption.setGiaSp(updatedProductOption.getGiaSp());
            productOption.setKhuyenMai(updatedProductOption.getKhuyenMai());
            productOption.setSoLuong(updatedProductOption.getSoLuong());
            return productOptionRepository.save(productOption);
        }
        return null;
    }

    @Transactional
    public void deleteProductOption(Integer id) {
        productOptionRepository.deleteById(id);
    }

    @Override
    public ProductOption getProductOptionById(Integer id) {
        return productOptionRepository.findById(id).orElse(null);
    }


    public List<ProductDTO> searchProducts(String keyword) {
        List<Product> products = productRepository.findByTenSpContainingIgnoreCase(keyword);
        return products.stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setIdSp(product.getIdSp());
            productDTO.setTenSp(product.getTenSp());
            productDTO.setThongTinSp(product.getThongTinSp());
            productDTO.setLinkAnh(product.getLinkAnh());
            productDTO.setBaoHanh(product.getBaoHanh());
            productDTO.setBaoMat(product.getBaoMat());
            productDTO.setCamera(product.getCamera());
            productDTO.setBoNho(product.getBoNho());
            productDTO.setCongSac(product.getCongSac());
            productDTO.setHeDieuHanh(product.getHeDieuHanh());
            productDTO.setManHinh(product.getManHinh());
            productDTO.setTrongLuong(product.getTrongLuong());
            productDTO.setPin(product.getPin());
            productDTO.setTheSim(product.getTheSim());
            productDTO.setLinkAnh(product.getLinkAnh());
            productDTO.setSoLuotBinhLuan(product.getSoLuotBinhLuan());
            productDTO.setSoLuotDanhGia(product.getSoLuotDanhGia());
            productDTO.setSoSaoTrungBinh(product.getSoSaoTrungBinh());
            productDTO.setProductOptions(product.getProductOptions().stream().map(option -> {
                ProductOptionDTO optionDTO = new ProductOptionDTO();
                optionDTO.setIdTuyChon(option.getIdTuyChon());
                optionDTO.setMauSac(option.getMauSac());
                optionDTO.setLinkMauAnhSp(option.getLinkMauAnhSp());
                optionDTO.setDungLuong(option.getDungLuong());
                optionDTO.setGiaSp(option.getGiaSp());
                optionDTO.setKhuyenMai(option.getKhuyenMai());
                optionDTO.setSoLuong(option.getSoLuong());
                optionDTO.setGiaSauKhuyenMai(optionDTO.getGiaSauKhuyenMai());
                return optionDTO;
            }).collect(Collectors.toList()));
            ProductCategoryDTO categoryDTO = new ProductCategoryDTO(); categoryDTO.setIdDm(product.getProductCategory().getIdDm()); categoryDTO.setTenDm(product.getProductCategory().getTenDm()); productDTO.setProductCategory(categoryDTO);

            return productDTO;
        }).collect(Collectors.toList());
    }



}
