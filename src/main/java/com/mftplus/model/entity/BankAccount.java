package com.mftplus.model.entity;

import com.mftplus.model.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.math.BigDecimal;


@Entity(name="BankAccountEntity")
@Table(name="BankAccount")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$",message = "Invalid Name")
    @Column(name="name", length = 20, nullable = false)
    private String name;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$",message = "Invalid Name")
    @Column(name="name", length = 20, nullable = false)
    private String family;

    private String accountNumber;

    private BigDecimal balance;

    private AccountType type;



}
