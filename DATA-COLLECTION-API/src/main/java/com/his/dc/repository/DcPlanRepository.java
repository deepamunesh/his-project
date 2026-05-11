package com.his.dc.repository;

import com.his.dc.entity.DcPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DcPlanRepository extends JpaRepository<DcPlanEntity,Long> {
    DcPlanEntity findByCaseNum(Long CaseNum);

    DcPlanEntity findByPlanId(Long planId);
}
