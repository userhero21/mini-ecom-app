package com.company.miniecom.services;

import com.company.miniecom.domains.Cart;
import com.company.miniecom.domains.CartItems;
import com.company.miniecom.domains.Product;
import com.company.miniecom.repository.CartItemsRepository;
import com.company.miniecom.repository.CartRepository;
import com.company.miniecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemsService cartItemsService;
    private final CartItemsRepository cartItemsRepository;


    public void removeFromCart(Cart cart, Product product, CartItems cartItems) {
        double v = Double.parseDouble(product.getPrice()) * cartItems.getQuantity();
        cart.setTotalPrice(cart.getTotalPrice() - v);

        cartRepository.save(cart);
        cartItemsRepository.delete(cartItems);
    }
}
