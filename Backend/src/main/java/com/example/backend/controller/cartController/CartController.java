package com.example.backend.controller.cartController;

import com.example.backend.dto.Cart.CartItemDTO;
import com.example.backend.entities.cart.CartItem;
import com.example.backend.services.cartService.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody CartItem cartItem) {
        cartService.addToCart(cartItem);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{idKh}")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable Integer idKh) {
        List<CartItemDTO> cartItems = cartService.getCartByUserId(idKh);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem) {
        CartItem updatedCartItem = cartService.updateCartItem(cartItem);
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/delete/{id}") 
    public ResponseEntity<Void> deleteCartItem(@PathVariable Integer id)
    { cartService.deleteCartItem(id); return ResponseEntity.ok().build(); }
}
