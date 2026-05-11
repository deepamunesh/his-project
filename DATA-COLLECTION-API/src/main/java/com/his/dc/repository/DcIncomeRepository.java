package com.his.dc.repository;

import com.his.dc.entity.DcIncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DcIncomeRepository extends JpaRepository<DcIncomeEntity,Long> {
    DcIncomeEntity findByCaseNum(Long caseNum);
    //DcIncomeEntity findByPlanId(Long caseNum);
}
