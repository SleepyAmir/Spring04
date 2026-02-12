package com.mftplus.mapper;


import com.mftplus.dto.BankAccountDto;
import com.mftplus.model.entity.BankAccount;
import org.mapstruct.Mapper;


@Mapper(componentModel="spring")
public interface BankAccountMapper {
    BankAccountDto toDto(BankAccount bankAccount);

    BankAccount toEntity(BankAccountDto bankAccountDto);




}
