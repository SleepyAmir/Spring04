package com.mftplus.service;

import com.mftplus.dto.BankAccountDto;
import com.mftplus.mapper.BankAccountMapper;
import com.mftplus.model.entity.BankAccount;
import com.mftplus.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;



    @Transactional
    @Override
    public void save(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDto);
        bankAccountRepository.save(bankAccount);
    }

    @Transactional
    @Override
    public void update(BankAccountDto bankAccountDto) {
        if (bankAccountDto.getId() == null){
            throw new IllegalArgumentException("ID cannot be null for update");
        }
        BankAccount bankAccount = bankAccountMapper.toEntity(bankAccountDto);
        bankAccountRepository.save(bankAccount);

    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bankAccountRepository.deleteById(id);

    }

    @Override
    public BankAccountDto findById(Long id) {
        return bankAccountRepository.findById(id)
                .map(bankAccountMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<BankAccountDto> findAll() {
        return bankAccountRepository.findAll()
                .stream()
                .map(bankAccountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BankAccountDto> findAll(Pageable pageable) {
        return bankAccountRepository.findAll(pageable)
                .map(bankAccountMapper::toDto);

    }

    @Transactional
    @Override
    public Page<BankAccountDto> findAllDeleted(Pageable pageable) {
        return bankAccountRepository.findAllDeleted(pageable)
                .map(bankAccountMapper::toDto);
    }

    @Override
    public Page<BankAccountDto> findAllEvenDeleted(Pageable pageable) {
        return bankAccountRepository.findAllEvenDeleted(pageable)
                .map(bankAccountMapper::toDto);
    }

    @Override
    public void restoreById(Long id) {
        bankAccountRepository.restoreById(id);

    }

    @Override
    public Page<BankAccountDto> findByNameAndFamily(String name, String family, Pageable pageable) {
        return bankAccountRepository.findByNameAndFamily(name,family,pageable)
                .map(bankAccountMapper::toDto);
    }

    @Override
    public Page<BankAccountDto> findByAccountNumber(String accountNumber, Pageable pageable) {
        return bankAccountRepository.findByAccountNumber(accountNumber,pageable)
                .map(bankAccountMapper::toDto);
    }

    @Override
    public Page<BankAccountDto> findByNameAndAccountNumber(String name, String accountNumber, Pageable pageable) {
        return bankAccountRepository.findByNameAndAccountNumber(accountNumber,pageable)
                .map(bankAccountMapper::toDto);
    }

}
