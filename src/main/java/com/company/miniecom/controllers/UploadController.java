package com.company.miniecom.controllers;

import com.company.miniecom.Bot;
import com.company.miniecom.domains.Product;
import com.company.miniecom.domains.enums.ProductStatus;
import com.company.miniecom.dto.ProductAddDTO;
import com.company.miniecom.repository.ProductRepository;
import com.company.miniecom.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final Bot bot;
    private final ProductService productService;
    private final ProductRepository productRepository;


    @PostMapping("/product")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public String productAdd(HttpServletRequest request,
                             @ModelAttribute ProductAddDTO productAddDTO,
                             Model model) {

        bot.sendMessage(request.getRemoteAddr(), "product upload");
        Product product = productService.add(productAddDTO);
        model.addAttribute(product);
        // TODO: 10/27/2022 fix
        return "redirect:/product?id=" + product.getId();
    }


    @PostMapping("img")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public String imageAdd(HttpServletRequest request,
                           @RequestParam("img") MultipartFile img,
                           @RequestParam("productId") Optional<Long> productId) {

        Product product = productRepository.findById(productId.get()).get();
        productService.addImg(img, product);

        bot.sendMessage(request.getRemoteAddr(), "image upload");
        // TODO: 10/28/2022 fix
        return "redirect:/product?id=" + productId.get();
    }


    @PostMapping("/change")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public String statusChange(HttpServletRequest request,
                               @RequestParam("productId") Optional<Long> productId) {

        Product product = productRepository.findById(productId.get()).get();

        switch (product.getStatus()) {
            case ACTIVE -> product.setStatus(ProductStatus.ARCHIVED);
            case ARCHIVED -> product.setStatus(ProductStatus.DISABLED);
            case DISABLED -> product.setStatus(ProductStatus.ACTIVE);
        }

        productRepository.save(product);
        bot.sendMessage(request.getRemoteAddr(), "status change");
        return "redirect:/manage/products";
    }


}
