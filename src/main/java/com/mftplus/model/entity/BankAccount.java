package com.mftplus.model.entity;

import com.mftplus.model.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


import java.math.BigDecimal;


@Entity(name="BankAccountEntity")
@Table(name="bank_aacount")
@SQLDelete(sql = "UPDATE bank_account SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$",message = "Invalid Name")
    @Column(name="name", length = 20, nullable = false)
    private String name;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$",message = "Invalid Name")
    @Column(name="family", length = 20, nullable = false)
    private String family;

    @Column(nullable = false, unique = true, length = 16)
    private String accountNumber;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

}
