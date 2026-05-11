package com.his.dc.repository;

import com.his.dc.entity.DcEducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DcEducationRepository extends JpaRepository<DcEducationEntity,Long> {
    DcEducationEntity findByCaseNum(Long caseNum);
}
