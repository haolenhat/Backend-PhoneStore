package com.example.backend.services.commentService;

import com.example.backend.entities.comment.Comment;
import com.example.backend.entities.product.Product;
import com.example.backend.entities.product.ProductOption;
import com.example.backend.repository.commentRepository.CommentRepository;
import com.example.backend.repository.productRepository.ProductOptionRepository;
import com.example.backend.repository.productRepository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Comment> getCommentsByProductId(Integer idSp) {
        return commentRepository.findByProductIdSp(idSp);
    }

    public Comment addComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        updateProductStats(comment.getProduct().getIdSp());
        return savedComment;
    }

    public Comment findById(Integer idBinhLuan) {
        Optional<Comment> optionalComment = commentRepository.findById(idBinhLuan);
        return optionalComment.orElse(null);
    }

    public Comment save(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        updateProductStats(comment.getProduct().getIdSp());
        return savedComment;
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
        updateProductStats(comment.getProduct().getIdSp());
    }

    private void updateProductStats(Integer productId) {
        int totalReviews = commentRepository.countByProductIdSp(productId);
        int totalComments = commentRepository.countByProductIdSpAndDanhGiaSaoGreaterThan(productId, 0);
        double averageRating = commentRepository.calculateAverageRating(productId);

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setSoLuotDanhGia(totalReviews);
            product.setSoLuotBinhLuan(totalComments);
            product.setSoSaoTrungBinh(averageRating);
            productRepository.save(product);
        }
    }
}
