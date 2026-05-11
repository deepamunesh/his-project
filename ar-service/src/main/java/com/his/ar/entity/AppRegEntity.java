package com.his.ar.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data

@Entity
@Table(name="APP_REG_DTLS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "SSN_NO")
        })
public class AppRegEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    )
    @Column(name="APP_ID")
    private Integer appid;   // match DB type (int)

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="GENDER")
    private String gender;

    @Column(name="SSN_NO")   // corrected spelling
    private String ssnNo;

    @Column(name="PHNO")
    private String phno;    // match DB type (int)

    @Column(name="EMAIL")
    private String email;
}
