package com.company.miniecom.controllers;

import com.company.miniecom.Bot;
import com.company.miniecom.domains.OrderLines;
import com.company.miniecom.domains.Product;
import com.company.miniecom.domains.enums.ProductCategorie;
import com.company.miniecom.domains.enums.ProductColors;
import com.company.miniecom.repository.OrderRepository;
import com.company.miniecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('MANAGER')")
public class AdminController {

    private final Bot bot;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    @GetMapping("/dashboard")
    public String dashboardPage(HttpServletRequest request,
                                Model model) {

        bot.sendMessage(request.getRemoteAddr(), "admin: dashboard page");

        double total = 0;
        for (OrderLines orderLines : orderRepository.findAll()) {
            total += orderLines.getCart().getTotalPrice();
        }

        model.addAttribute("totalSales", new DecimalFormat("#.##").format(total));
        model.addAttribute("productsCount", productRepository.count());
        model.addAttribute("ordersCount", orderRepository.count());
        return "manager/index";
    }


    @GetMapping("/add")
    public String addPage(HttpServletRequest request,
                          Model model) {

        bot.sendMessage(request.getRemoteAddr(), "admin: product add page");

        model.addAttribute("categories", ProductCategorie.values());
        model.addAttribute("colors", ProductColors.values());
        return "manager/product_add";
    }


    @GetMapping("/update{productId}")
    public String updatePage(Model model, @PathVariable Long productId) {

        Optional<Product> byId = productRepository.findById(productId);

        model.addAttribute("product", byId.get());
        model.addAttribute("categories", ProductCategorie.values());
        model.addAttribute("colors", ProductColors.values());
        return "manager/product_update";
    }


    @GetMapping("/manage/products")
    public String productPage(HttpServletRequest request,
                              @RequestParam("page") Optional<Integer> page,
                              Model model) {

        bot.sendMessage(request.getRemoteAddr(), "admin: products page");
        PageRequest pageRequest = PageRequest.of(page.orElse(0), 12);

        Page<Product> all = productRepository.findAll(pageRequest);
        model.addAttribute("page", all);
        return "manager/products";
    }


    @GetMapping("/manage/orders")
    public String ordersPage(HttpServletRequest request,
                             Model model) {

        bot.sendMessage(request.getRemoteAddr(), "admin: order page");
        model.addAttribute("orders", orderRepository.findAll());
        return "manager/tables";
    }

    @GetMapping("/reviews")
    public String reviewsPage(HttpServletRequest request) {
        bot.sendMessage(request.getRemoteAddr(), "admin: review page");
        return "404";
    }


}
