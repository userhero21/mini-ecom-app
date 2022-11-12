package com.company.miniecom.controllers;

import com.company.miniecom.Bot;
import com.company.miniecom.dto.LoginDTO;
import com.company.miniecom.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final Bot bot;
    private final AuthService service;


    @GetMapping("/signin")
    public String loginPage(HttpServletRequest request) {
        bot.sendMessage(request.getRemoteAddr(), "login page");
        return "auth/login2";
    }


    @GetMapping("/signup")
    public String registerPage(HttpServletRequest request, LoginDTO loginDTO) {
        bot.sendMessage(request.getRemoteAddr(), "register page");
        return "auth/register2";
    }


    @PostMapping("/signup")
    public String register(HttpServletRequest request,
                           @Valid @ModelAttribute("loginDTO") LoginDTO loginDto,
                           BindingResult result) {

        if (result.hasErrors()) {
            return "/auth/register2";
        }

        bot.sendMessage(request.getRemoteAddr(), "register");
        service.register(loginDto);
        return "index";
    }


    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public String logoutPage(HttpServletRequest request) {
        bot.sendMessage(request.getRemoteAddr(), "logout page");
        return "auth/logout";
    }


}
