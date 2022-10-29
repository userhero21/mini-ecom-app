package com.company.miniecom.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAddDTO {
    @NotEmpty(message = "Name cannot be empty")
    private String productName;
    private String productBrand;
    private String productCategory;
    private String productColor;
    @NotEmpty(message = "Description cannot be empty")
    private String productShortDes;
    private String productFullDes;
    @NotEmpty(message = "Image cannot be empty")
    private MultipartFile productImg;
    @NotEmpty(message = "Price cannot be empty")
    private String productPrice;
    private String productStatus;
}
