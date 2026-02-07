package com.mftplus.service;

import com.mftplus.dto.PersonDto; // کلاس DTO ایمپورت شد
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {
    void save(PersonDto personDto);
    void update(PersonDto personDto);

    void deleteById(Long id);

    PersonDto findById(Long id);

    List<PersonDto> findAll();
    Page<PersonDto> findAll(Pageable pageable);

    Page<PersonDto> findAllDeleted(Pageable pageable);
    Page<PersonDto> findAllEvenDeleted(Pageable pageable);

    void restoreById(Long id);

    Page<PersonDto> findByFamilyAndName(String family, String name, Pageable pageable);
}