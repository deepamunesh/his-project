package com.his.bi.repository;

import com.his.bi.entity.EdEligEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdEligRepository extends JpaRepository<EdEligEntity, Integer> {
    List<EdEligEntity> findByPlanStatus(String status);
}
