package com.example.backend.services.cartService;

import com.example.backend.dto.Cart.CartItemDTO;
import com.example.backend.entities.cart.CartItem;
import com.example.backend.repository.cartRepository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    @Transactional
    @Override
    public CartItem addToCart(CartItem newCartItem) {
        Optional<CartItem> existingCartItem = cartRepository.findByUser_IdKhAndProductOption_IdTuyChon(
                newCartItem.getUser().getIdKh(),
                newCartItem.getProductOption().getIdTuyChon()
        );

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setSoLuong(cartItem.getSoLuong() + newCartItem.getSoLuong()); // Tăng số lượng sản phẩm
            return cartRepository.save(cartItem);
        } else {
            return cartRepository.save(newCartItem);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<CartItemDTO> getCartByUserId(Integer idKh) {
        List<CartItem> cartItems = cartRepository.findByUserIdKh(idKh);
        return cartItems.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        return cartRepository.save(cartItem);
    }

    private CartItemDTO convertToDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setIdGioHang(cartItem.getIdGioHang());
        dto.setSoLuong(cartItem.getSoLuong());
        dto.setNgayTao(cartItem.getNgayTao());

        CartItemDTO.UserDTO userDTO = new CartItemDTO.UserDTO();
        userDTO.setIdKh(cartItem.getUser().getIdKh());
        userDTO.setTenKh(cartItem.getUser().getTenKh());
        userDTO.setMail(cartItem.getUser().getMail());
        dto.setUser(userDTO);

        CartItemDTO.ProductOptionDTO productOptionDTO = new CartItemDTO.ProductOptionDTO();
        productOptionDTO.setIdTuyChon(cartItem.getProductOption().getIdTuyChon());
        productOptionDTO.setMauSac(cartItem.getProductOption().getMauSac());
        productOptionDTO.setDungLuong(cartItem.getProductOption().getDungLuong());
        productOptionDTO.setGiaSp(cartItem.getProductOption().getGiaSp() * (1 - cartItem.getProductOption().getKhuyenMai()/100));
        productOptionDTO.setTenSp(cartItem.getProductOption().getProduct().getTenSp());
        productOptionDTO.setLinkAnh(cartItem.getProductOption().getLinkMauAnhSp());
        dto.setProductOption(productOptionDTO);

        return dto;
    }

    @Transactional
    @Override
    public void deleteCartItem(Integer id) {
        cartRepository.deleteById(id);
    }
}
