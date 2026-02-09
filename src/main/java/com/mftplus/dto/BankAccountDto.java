package com.mftplus.dto;

import com.mftplus.model.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Data
@Mapper(componentModel = "spring")
public class BankAccountDto {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid Name")
    private String name;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid Name")
    private String family;

    private String accountNumber;

    @NotBlank
    @Positive
    private BigDecimal balance;


    private AccountType type;


}
