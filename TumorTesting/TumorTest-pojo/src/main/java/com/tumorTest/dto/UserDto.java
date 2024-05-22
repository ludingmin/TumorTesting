package com.tumorTest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDto {
    public Long id;
    public String name;
    public String number;
    public Integer age;
    public Integer Type;
}
