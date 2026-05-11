package com.his.dc.repository;

import com.his.dc.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Integer>
{
    public AccountEntity findBySsn(String ssn);
    Optional<AccountEntity> findByEmail(String email);
    Optional<AccountEntity> findByUserName(String userName);
}

