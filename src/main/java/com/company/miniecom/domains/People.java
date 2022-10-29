package com.company.miniecom.domains;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class People {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;
}


