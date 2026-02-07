package com.mftplus.dto;

import com.mftplus.model.enums.AccountType;
import org.mapstruct.Mapper;

import java.math.BigDecimal;


@Mapper(componentModel = "spring")
public class BankAccountDto {

    private long id;
    private String name;
    private String family;
    private String accountNumber;
    private BigDecimal balance;
    private AccountType type;

}
