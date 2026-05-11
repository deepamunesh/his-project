package com.his.ed.reposirory;

import com.his.ed.entity.EdEligEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EdEligRepository extends JpaRepository<EdEligEntity, Integer> {
    EdEligEntity findByCaseNum(Long caseNum);

}
