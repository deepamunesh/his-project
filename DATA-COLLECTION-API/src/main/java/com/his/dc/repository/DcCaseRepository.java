package com.his.dc.repository;

import com.his.dc.entity.DcCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DcCaseRepository extends JpaRepository<DcCaseEntity,Long> {
    //@Override
    Optional<DcCaseEntity> findById(Long aLong);
}
