package com.company.miniecom.domains;

import com.company.miniecom.domains.enums.ProductCategorie;
import com.company.miniecom.domains.enums.ProductColors;
import com.company.miniecom.domains.enums.ProductStatus;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    @Enumerated(EnumType.STRING)
    private ProductCategorie category;

    @Enumerated(EnumType.STRING)
    private ProductColors color;

    private String img;

    private String short_des;

    private String full_des;

    private String price;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "product")
    private List<ProductImages> images;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;


}
