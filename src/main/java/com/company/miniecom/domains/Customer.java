package com.company.miniecom.domains;

import com.company.miniecom.domains.auth.AuthUser;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private AuthUser authUser;

    @OneToOne
    private Address address;

    private String firstName;

    private String lastName;

    private String phoneNumber;


}
