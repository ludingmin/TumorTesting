package com.tumorTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUseDto {
    private String name;
    private String number;
    private String password;
    private Integer age;
    private String address;
    private String postcode;
}
