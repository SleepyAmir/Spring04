package com.mftplus.repository;

import com.mftplus.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(
            value = "SELECT * FROM persons WHERE deleted = true",
            countQuery = "SELECT count(*) FROM persons WHERE deleted = true",
            nativeQuery = true
    )
    Page<Person> findAllDeleted(Pageable pageable);

    @Query(
            value = "SELECT * FROM persons",
            countQuery = "SELECT count(*) FROM persons",
            nativeQuery = true
    )
    Page<Person> findAllEvenDeleted(Pageable pageable);

    // بازیابی رکورد
    @Transactional
    @Modifying
    @Query(value = "UPDATE persons SET deleted = false WHERE id = :id", nativeQuery = true)
    void restoreById(@Param("id") Long id);

    Page<Person> findByFamilyAndName(String family, String name, Pageable pageable);
}