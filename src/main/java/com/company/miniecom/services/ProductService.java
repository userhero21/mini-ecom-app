package com.company.miniecom.services;

import com.company.miniecom.Bot;
import com.company.miniecom.domains.Product;
import com.company.miniecom.domains.ProductImages;
import com.company.miniecom.domains.enums.ProductCategorie;
import com.company.miniecom.domains.enums.ProductColors;
import com.company.miniecom.domains.enums.ProductStatus;
import com.company.miniecom.dto.ProductAddDTO;
import com.company.miniecom.repository.ProductImagesRepository;
import com.company.miniecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final Bot bot;
    private final ProductImagesRepository productImagesRepository;
    private final ProductRepository repository;
    @Value("${img.upload.location}")
    String location;


    public Product add(ProductAddDTO dto) {
        String generatedImgName = manageImg(dto.getProductImg());
        Product build = Product.builder()
                .name(dto.getProductName())
                .brand(dto.getProductBrand())
                .category(ProductCategorie.valueOf(dto.getProductCategory()))
                .color(ProductColors.valueOf(dto.getProductColor()))
                .img(generatedImgName)
                .short_des(dto.getProductShortDes())
                .full_des(dto.getProductFullDes())
                .price(dto.getProductPrice())
                .status(ProductStatus.valueOf(dto.getProductStatus()))
                .build();

        return repository.save(build);
    }

    public void addImg(MultipartFile image, Product product) {
        String generatedImgName = manageImg(image);
        ProductImages build = ProductImages.builder()
                .product(product)
                .img(generatedImgName)
                .build();

        productImagesRepository.save(build);
    }

    private String manageImg(MultipartFile image) {
        String generatedValue = String.valueOf(System.currentTimeMillis());
        String imageType = ".jpg";

        try {
            image.transferTo(new File(location + generatedValue + imageType));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return generatedValue + imageType;
    }

    public Page<Product> getProducts(Optional<Integer> integer,
                                     Optional<String> search,
                                     Optional<String> category,
                                     Optional<String> color) {

        Page<Product> products;
        // TODO: 10/28/2022 fix
        PageRequest pageRequest = PageRequest.of(integer.orElse(0), 12);
        String forBot = "\n\t- pagi: " + integer;

//        if (search.isPresent()) {
//            products = repository.getProductsBySearch(
//                    search.get(),
//                    pageRequest,
//                    ProductStatus.ACTIVE);
//
//        } else

        if (category.isPresent()) {
            products = repository.getProductsByCategoryAndStatus(
                    ProductCategorie.valueOf(category.get()),
                    pageRequest,
                    ProductStatus.ACTIVE);
            forBot = forBot + "\n\t- category: " + category.get();

        } else if (color.isPresent()) {
            products = repository.getProductsByColorAndStatus(
                    ProductColors.valueOf(color.get()),
                    pageRequest,
                    ProductStatus.ACTIVE);
            forBot = forBot + "\n\t- color: " + color.get();

        } else {
            products = repository.getProductsByStatus(
                    pageRequest,
                    ProductStatus.ACTIVE);

        }

        bot.sendMessage(null, forBot);
        return products;

    }

    public Product getProduct(Long id) {
        // TODO: 10/28/2022 fix
        Product product = repository.getProductByIdAndStatus(id, ProductStatus.ACTIVE);

        return product;
    }
}
