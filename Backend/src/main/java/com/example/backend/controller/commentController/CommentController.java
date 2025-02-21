package com.example.backend.controller.commentController;

import com.example.backend.entities.comment.Comment;
import com.example.backend.entities.user.User;
import com.example.backend.repository.userRepository.UserRepository;
import com.example.backend.services.commentService.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.backend.entities.product.Product;
import com.example.backend.entities.user.User;
import com.example.backend.repository.productRepository.ProductRepository;


import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product/{idSp}")
    public ResponseEntity<List<Comment>> getCommentsByProductId(@PathVariable Integer idSp) {
        List<Comment> comments = commentService.getCommentsByProductId(idSp);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/user/{idKh}")
    public ResponseEntity<Comment> addComment(@PathVariable int idKh, @RequestBody Comment comment) {
        Optional<User> userOptional = userRepository.findByIdKh(idKh);
        Optional<Product> productOptional = productRepository.findById(comment.getProduct().getIdSp());

        if (userOptional.isPresent() && productOptional.isPresent()) {
            User user = userOptional.get();
            comment.setUser(user);
            Comment newComment = commentService.addComment(comment);
            return ResponseEntity.ok(newComment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/user/{idKh}/comment/{idBinhLuan}")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer idKh, @PathVariable Integer idBinhLuan, @RequestBody Comment commentDetails) {
        Comment comment = commentService.findById(idBinhLuan);

        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (comment.getUser().getIdKh().equals(idKh)) {
            comment.setNoiDung(commentDetails.getNoiDung());
            comment.setDanhGiaSao(commentDetails.getDanhGiaSao());
            Comment updatedComment = commentService.save(comment);
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/user/{idKh}/comment/{idBinhLuan}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer idKh, @PathVariable Integer idBinhLuan) {
        Comment comment = commentService.findById(idBinhLuan);

        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (comment.getUser().getIdKh().equals(idKh)) {
            commentService.delete(comment);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
