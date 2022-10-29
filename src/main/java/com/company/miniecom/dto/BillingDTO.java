package com.company.miniecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String notes;
}
