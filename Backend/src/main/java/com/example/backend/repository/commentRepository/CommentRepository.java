package com.example.backend.repository.commentRepository;
import com.example.backend.entities.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByProductIdSp(Integer idSp);

    int countByProductIdSp(Integer idSp);
    int countByProductIdSpAndDanhGiaSaoGreaterThan(Integer idSp, int danhGiaSao);

    @Query("SELECT AVG(c.danhGiaSao) FROM Comment c WHERE c.product.idSp = :idSp")
    double calculateAverageRating(Integer idSp);
}
