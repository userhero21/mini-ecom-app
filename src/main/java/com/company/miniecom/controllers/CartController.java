package com.company.miniecom.controllers;

import com.company.miniecom.Bot;
import com.company.miniecom.configs.security.UserDetails;
import com.company.miniecom.domains.Cart;
import com.company.miniecom.domains.CartItems;
import com.company.miniecom.domains.Product;
import com.company.miniecom.domains.enums.CartStatus;
import com.company.miniecom.repository.AuthRepository;
import com.company.miniecom.repository.CartItemsRepository;
import com.company.miniecom.repository.CartRepository;
import com.company.miniecom.repository.ProductRepository;
import com.company.miniecom.services.CardService;
import com.company.miniecom.services.CartItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final Bot bot;
    private final CardService cartService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemsService cartItemsService;
    private final CartItemsRepository cartItemsRepository;


    @GetMapping
    public ModelAndView cartPage(HttpServletRequest request,
                                 ModelAndView modelAndView,
                                 Authentication authentication) {

        bot.sendMessage(request.getRemoteAddr(), "cart page");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Cart cart = cartRepository.getCartByAuthUser_UsernameAndStatus(userDetails.getUsername(), CartStatus.TEMP);

        modelAndView.setViewName("shopping-cart");
        modelAndView.addObject("cart", cart);

        modelAndView.addObject("total", new DecimalFormat("#.##").format(cart.getTotalPrice()));

        return modelAndView;
    }


    @PostMapping
    @PreAuthorize("permitAll()")
    public String addToCart(HttpServletRequest request,
                            Authentication authentication,
                            @RequestParam("quantity") Optional<Integer> quantity,
                            @RequestParam("productId") Long productId) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Cart cart = cartRepository.getCartByAuthUser_UsernameAndStatus(userDetails.getUsername(), CartStatus.TEMP);
        Product product = productRepository.findById(productId).get();

        cartItemsService.addToCartItems(quantity.orElse(1), cart, product);

        bot.sendMessage(request.getRemoteAddr(), "product add cart id: " + productId);
        return "redirect:cart";
    }

    @PostMapping("/remove")
    @PreAuthorize("permitAll()")
    public String removeFromCart(HttpServletRequest request,
                                 Authentication authentication,
                                 @RequestParam("productId") Long productId) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Cart cart = cartRepository.getCartByAuthUser_UsernameAndStatus(userDetails.getUsername(), CartStatus.TEMP);
        Product product = productRepository.findById(productId).get();
        CartItems cartItems = cartItemsRepository.getCartItemsByCartAndProduct(cart, product);

        cartService.removeFromCart(cart, product, cartItems);

        bot.sendMessage(request.getRemoteAddr(), "product remove from cart id: " + productId);
        return "redirect:/cart";
    }

//    @PostMapping("/cart/checkout")
//    @PreAuthorize("permitAll()")
//    public String checkout(
//            Authentication authentication
//    ) {
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        AuthUser authUser = authRepository.findByUsername(userDetails.getUsername()).get();
//        Cart cart = cartRepository.getCartByAuthUser_Username(userDetails.getUsername());
//
//        if (cart.getCartItems().isEmpty()) {
//            // TODO: 8/12/2022 custom check
//        }
//
//        Customer customer = customerRepository.getCustomerByAuthUser_Username(userDetails.getUsername());
//        if (customer == null) {
//            // TODO: 8/12/2022
//        }
//
//
//
//    }

}
