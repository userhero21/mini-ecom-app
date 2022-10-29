package com.company.miniecom.controllers;

import com.company.miniecom.Bot;
import com.company.miniecom.domains.Product;
import com.company.miniecom.domains.enums.ProductCategorie;
import com.company.miniecom.domains.enums.ProductStatus;
import com.company.miniecom.dto.PeopleCreateDTO;
import com.company.miniecom.repository.ProductRepository;
import com.company.miniecom.services.PeopleService;
import com.company.miniecom.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PagesController {

    private final Bot bot;
    private final PeopleService peopleService;
    private final ProductService productService;


    @GetMapping("/")
    public String homePage(HttpServletRequest request) {
        bot.sendMessage(request.getRemoteAddr(), "home page");
        return "index";
    }

    @PostMapping("/")
    public String add(@ModelAttribute PeopleCreateDTO dto) {
        peopleService.add(dto);
        return "index";
    }

    @GetMapping("products")
    public ModelAndView addProduct(HttpServletRequest request,
                                   @RequestParam Optional<Integer> pagi,
                                   @RequestParam Optional<String> search,
                                   @RequestParam Optional<String> category,
                                   @RequestParam Optional<String> color,
                                   ModelAndView modelAndView) {

        System.out.println(request);

        bot.sendMessage(request.getRemoteAddr(), "products page");
        Page<Product> products = productService.getProducts(pagi, search, category, color);

        modelAndView.setViewName("shop");
        modelAndView.addObject("page", products);
        modelAndView.addObject("categories", ProductCategorie.values());

        return modelAndView;
    }

    @GetMapping("product")
    public ModelAndView getProductPage(HttpServletRequest request,
                                       @RequestParam Optional<Long> id,
                                       ModelAndView modelAndView) {
        if (id.isPresent()) {
            bot.sendMessage(request.getRemoteAddr(), "product id: " + id);
            modelAndView.setViewName("shop-details");
            modelAndView.addObject("product", productService.getProduct(id.get()));

        } else {
            bot.sendMessage(request.getRemoteAddr(), "product page - error");
            modelAndView.setViewName("404");
        }

        return modelAndView;
    }

    @GetMapping("about")
    public String getAboutPage(HttpServletRequest request) {
        bot.sendMessage(request.getRemoteAddr(), "about page");
        return "about";
    }

    @GetMapping("blog")
    public String getBlogPage(HttpServletRequest request) {
        bot.sendMessage(request.getRemoteAddr(), "blog page");
        return "blog";
    }

    @GetMapping("contact")
    public String getContactPage(HttpServletRequest request) {
        bot.sendMessage(request.getRemoteAddr(), "contact page");
        return "contact";
    }


}
