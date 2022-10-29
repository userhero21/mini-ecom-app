package com.company.miniecom.repository;

import com.company.miniecom.domains.Cart;
import com.company.miniecom.domains.CartItems;
import com.company.miniecom.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    CartItems getCartItemsByCartAndProduct(Cart cart, Product product);

    @Transactional
    void removeCartItemsByCartAndProduct(Cart cart, Product product);
}
