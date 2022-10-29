package com.company.miniecom.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeopleCreateDTO {
    private String firstName;
    private String lastName;
}
