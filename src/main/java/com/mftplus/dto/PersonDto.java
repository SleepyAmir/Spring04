package com.mftplus.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PersonDto {
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid Name")
    private String name;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid Family")
    private String family;
}