package com.his.ed.reposirory;

import com.his.ed.entity.DcEducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DcEducationRepository extends JpaRepository<DcEducationEntity,Long> {
    DcEducationEntity findByCaseNum(Long caseNum);
}
