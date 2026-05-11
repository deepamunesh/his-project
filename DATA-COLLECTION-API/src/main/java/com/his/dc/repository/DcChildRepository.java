package com.his.dc.repository;

import com.his.dc.entity.DcChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DcChildRepository extends JpaRepository<DcChildEntity,Long > {
    List<DcChildEntity> findByCaseNum(Long caseNum);
}
