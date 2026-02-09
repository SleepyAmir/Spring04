package com.mftplus.service;

import com.mftplus.dto.BankAccountDto;
import com.mftplus.model.enums.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankAccountService {
    void save(BankAccountDto bankAccountDto);
    void update(BankAccountDto bankAccountDto);
    void deleteById (Long id);

    BankAccountDto findById(Long id);

    List<BankAccountDto> findAll();

    Page<BankAccountDto> findAll(Pageable pageable);

    Page<BankAccountDto> findAllDeleted(Pageable pageable);
    Page<BankAccountDto> findAllEvenDeleted(Pageable pageable);

    void restoreById(Long id);

    Page<BankAccountDto> findByNameAndFamily(String name,String family,Pageable pageable);

    Page<BankAccountDto>findByAccountNumber(String accountNumber,Pageable pageable);

    Page<BankAccountDto>findByNameAndAccountNumber(String name,String accountNumber,Pageable pageable);

    BankAccountDto issueAccount(Long id, AccountType accountType, BankAccountDto bankAccountDto);

}
