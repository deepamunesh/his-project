package com.his.ed.reposirory;

import com.his.ed.entity.DcIncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DcIncomeRepository extends JpaRepository<DcIncomeEntity,Long> {
    DcIncomeEntity findByCaseNum(Long caseNum);
    //DcIncomeEntity findByPlanId(Long caseNum);
}
