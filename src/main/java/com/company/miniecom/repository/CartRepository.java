package com.company.miniecom.repository;

import com.company.miniecom.domains.Cart;
import com.company.miniecom.domains.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

//    Cart getCartByAuthUser_Username(String username);

//    @Query(value = "SELECT t.* FROM cart t WHERE authuser_id = ?1 and isordering = ?2", nativeQuery = true)
//    Cart getCartByAuthIdAndOrdering(Long authUserId, Boolean isOrdering);

    Cart getCartByAuthUser_UsernameAndStatus(String username, CartStatus status);
}
