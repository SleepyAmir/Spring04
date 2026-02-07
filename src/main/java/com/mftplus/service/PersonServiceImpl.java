package com.mftplus.service;

import com.mftplus.dto.PersonDto;
import com.mftplus.mapper.PersonMapper;
import com.mftplus.model.entity.Person;
import com.mftplus.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // تزریق خودکار Repository و Mapper
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Transactional
    @Override
    public void save(PersonDto personDto) {
        // 1. تبدیل DTO به Entity
        Person person = personMapper.toEntity(personDto);
        // 2. ذخیره Entity
        personRepository.save(person);
    }

    @Transactional
    @Override
    public void update(PersonDto personDto) {
        // برای آپدیت مطمئن می‌شویم ID وجود دارد
        if (personDto.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }
        Person person = personMapper.toEntity(personDto);
        personRepository.save(person);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public PersonDto findById(Long id) {
        return personRepository.findById(id)
                .map(personMapper::toDto) // تبدیل Entity پیدا شده به DTO
                .orElse(null); // یا پرتاب ResourceNotFoundException
    }

    @Override
    public List<PersonDto> findAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toDto) // تبدیل لیست Entity به لیست DTO
                .collect(Collectors.toList());
    }

    @Override
    public Page<PersonDto> findAll(Pageable pageable) {
        return personRepository.findAll(pageable)
                .map(personMapper::toDto); // متد map در Page به صورت خودکار تبدیل را انجام می‌دهد
    }

    @Override
    public Page<PersonDto> findAllDeleted(Pageable pageable) {
        return personRepository.findAllDeleted(pageable)
                .map(personMapper::toDto);
    }

    @Override
    public Page<PersonDto> findAllEvenDeleted(Pageable pageable) {
        return personRepository.findAllEvenDeleted(pageable)
                .map(personMapper::toDto);
    }

    @Transactional
    @Override
    public void restoreById(Long id) {
        personRepository.restoreById(id);
    }

    @Override
    public Page<PersonDto> findByFamilyAndName(String family, String name, Pageable pageable) {
        return personRepository.findByFamilyAndName(family, name, pageable)
                .map(personMapper::toDto);
    }
}