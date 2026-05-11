package com.his.correspondence.repository;

import com.his.correspondence.entity.CoTriggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CoTriggerRepository extends JpaRepository<CoTriggerEntity,Integer> {
    List<CoTriggerEntity> findByStatus(String status);

    //New method: fetch citizen email by CASE_NUM using join
    @Query(value = "SELECT a.EMAIL FROM DC_CASES d JOIN APP_REG_DTLS a ON d.APP_ID = a.APP_ID WHERE d.CASE_NUM = :caseNum", nativeQuery = true)
    String findCitizenEmailByCaseNum(Long caseNum);
}
