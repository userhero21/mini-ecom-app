package com.company.miniecom.domains;

import com.company.miniecom.domains.auth.AuthUser;
import com.company.miniecom.domains.enums.CartStatus;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    private AuthUser authUser;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "cart")
    private List<CartItems> cartItems = new ArrayList<>();

    private double totalPrice;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private CartStatus status = CartStatus.TEMP;

}
