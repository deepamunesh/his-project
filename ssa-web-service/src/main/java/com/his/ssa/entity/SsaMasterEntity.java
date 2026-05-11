package com.his.ssa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name="SSA_MASTER")
public class SsaMasterEntity
{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SSA_ID")
    private Long ssaId;

    @Column(name="DOB")
    private LocalDate dob;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="GENDER")
     private String gender;

    @Column(name="LAST_NAME")
     private String lastName;

    @Column(name="SSN_NO",unique=true,nullable=false)
            private String ssnNo;

    @Column(name="STATE_NAME")
      private String stateName;

    @Column(name="ACTIVE_SW")
      private String activeSw;

    @Column(name="PHNO")
    private String phNo;
}