package com.example.backend.repository.cartRepository;

import com.example.backend.entities.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer> {
    @Query("SELECT c FROM CartItem c WHERE c.user.idKh = :idKh")
    List<CartItem> findByUserIdKh(@Param("idKh") Integer idKh);

     CartItem findByUserIdKhAndProductOptionIdTuyChon(int idKh, int idTuyChon);

    ; Optional<CartItem> findByUser_IdKhAndProductOption_IdTuyChon(Integer userId, Integer productOptionId);

}
