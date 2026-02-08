package com.mftplus.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity(name="personEntity")
@Table(name="persons")

@SQLDelete(sql = "UPDATE persons SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$",message = "Invalid Name")
    @Column(name="name", length = 20, nullable = false)
    private String name;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$",message = "Invalid Family")
    @Column(name="family", length = 20, nullable = false)
    private String family;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
}
