package com.his.ssa.repository;

import com.his.ssa.entity.SsaMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SsaRepository extends JpaRepository<SsaMasterEntity,Long> {
                                                // 123456789
    Optional<SsaMasterEntity> findBySsnNo(String ssnNo);
}

//SSA_ID,    DOB,         FIRST_NAME   GENDER,      LAST_NAME,       SSN_NO,        STATE_NAME,        ACTIVE_SW
//'1',      '1990-05-10', 'John',       'MALE',        'Smith',       '123456789',    'Kentucky',         'Y'
