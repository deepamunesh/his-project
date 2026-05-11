package com.his.dc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="ACCOUNT_DTLS")
@Data
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACC_ID")
    private Integer accId;

    @Column(name = "USERNAME", unique = true)
    private String userName;

    @Column(name = "FNAME")
    private String fName;

    @Column(name = "LNAME")
    private String lName;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "SSN")
    private String ssn;

    @Column(name = "PHNO")
    private String phno;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @Column(name = "ACTIVE_SW")
    private String activeSw;

    @Column(name = "CREATED_DT")
    private LocalDateTime createdDt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_DT")
    private LocalDateTime updatedDt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

}