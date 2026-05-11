package com.his.ar.repository;

import com.his.ar.entity.AppRegEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRegRepository extends JpaRepository<AppRegEntity,Integer> {
    boolean existsBySsnNo(String ssnNo);

}
