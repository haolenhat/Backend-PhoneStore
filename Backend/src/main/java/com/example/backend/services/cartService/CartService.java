package com.example.backend.services.cartService;

import com.example.backend.dto.Cart.CartItemDTO;
import com.example.backend.entities.cart.CartItem;
import java.util.List;

public interface CartService {
    CartItem addToCart(CartItem cartItem);
    List<CartItemDTO> getCartByUserId(Integer idKh);
    CartItem updateCartItem(CartItem cartItem);
    void deleteCartItem(Integer id);
}
