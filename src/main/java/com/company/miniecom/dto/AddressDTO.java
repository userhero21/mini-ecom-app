package com.company.miniecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private String streetAddress;
    private String city;
    private String countryState;
    private String zipcode;
}
