package com.his.bi.repository;

import com.his.bi.entity.BiPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiPaymentRepository extends JpaRepository<BiPaymentEntity, Long> {
}