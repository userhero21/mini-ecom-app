package com.company.miniecom.repository;

import com.company.miniecom.domains.Product;
import com.company.miniecom.domains.enums.ProductCategorie;
import com.company.miniecom.domains.enums.ProductColors;
import com.company.miniecom.domains.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> getProductsByStatus(Pageable pageable, ProductStatus status);

    Page<Product> getProductsByCategoryAndStatus(ProductCategorie productCategorie, Pageable pageable, ProductStatus status);

    Page<Product> getProductsByColorAndStatus(ProductColors productColors, Pageable pageable, ProductStatus status);

    Product getProductByIdAndStatus(Long id, ProductStatus status);

//    @Query("from Product t where t.name like ':search%' or t.brand like ':search%'")
//    Page<Product> getProductsBySearch(String search, Pageable pageable, ProductStatus status);


}
