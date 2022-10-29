package com.company.miniecom.controllers;

import com.company.miniecom.Bot;
import com.company.miniecom.configs.security.UserDetails;
import com.company.miniecom.domains.Cart;
import com.company.miniecom.domains.auth.AuthUser;
import com.company.miniecom.domains.enums.CartStatus;
import com.company.miniecom.dto.AddressDTO;
import com.company.miniecom.dto.BillingDTO;
import com.company.miniecom.repository.AuthRepository;
import com.company.miniecom.repository.CartRepository;
import com.company.miniecom.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final Bot bot;
    private final OrderService orderService;
    private final CartRepository cartRepository;
    private final AuthRepository authRepository;


    @GetMapping("/checkout")
    public String checkoutPage(HttpServletRequest request,
                               Authentication authentication,
                               Model model) {

        bot.sendMessage(request.getRemoteAddr(), "checkout page");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Cart cart = cartRepository.getCartByAuthUser_UsernameAndStatus(userDetails.getUsername(), CartStatus.TEMP);

        model.addAttribute(cart);
        return "checkout";
    }


    @PostMapping("/order")
    public String placeOrder(HttpServletRequest request,
                             Authentication authentication,
                             @ModelAttribute BillingDTO billingDTO,
                             @ModelAttribute AddressDTO addressDTO,
                             @RequestParam("password") String password) {

        bot.sendMessage(request.getRemoteAddr(), "order page");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!password.equals(userDetails.getPassword())) {
            // TODO: 8/14/2022 any exception
        }

        Cart cart = cartRepository.getCartByAuthUser_UsernameAndStatus(userDetails.getUsername(), CartStatus.TEMP);
        if (cart.getCartItems().size() == 0) {
            // TODO: 8/14/2022 any exception
        }

        orderService.create(billingDTO, addressDTO, cart);

        Optional<AuthUser> optionalAuthUser = authRepository.findByUsername(userDetails.getUsername());
        cartRepository.save(Cart.builder().authUser(optionalAuthUser.get()).build());

        bot.sendMessage(null, "order successfully created");
        return "order-success";
    }


}
