package com.company.miniecom.services;

import com.company.miniecom.domains.Cart;
import com.company.miniecom.domains.CartItems;
import com.company.miniecom.domains.Product;
import com.company.miniecom.repository.CartItemsRepository;
import com.company.miniecom.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemsService {

    private final CartItemsRepository cartItemsRepository;
    private final CartRepository cartRepository;


    public void addToCartItems(Integer quantity, Cart cart, Product product) {

        CartItems cartItemsByCartAndProduct = cartItemsRepository.getCartItemsByCartAndProduct(cart, product);
        if (cartItemsByCartAndProduct != null) {
            cartItemsByCartAndProduct.setQuantity(cartItemsByCartAndProduct.getQuantity() + quantity);
            cartItemsRepository.save(cartItemsByCartAndProduct);
        } else {
            CartItems build = CartItems.builder()
                    .quantity(quantity)
                    .cart(cart)
                    .product(product)
                    .build();

            cartItemsRepository.save(build);
        }

        double v = Double.parseDouble(product.getPrice()) * quantity;
        cart.setTotalPrice(cart.getTotalPrice() + v);

        cartRepository.save(cart);
    }

}
