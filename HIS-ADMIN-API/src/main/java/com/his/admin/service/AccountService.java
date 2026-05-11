package com.his.admin.service;

import com.his.admin.entity.AccountEntity;
import com.his.admin.entity.Role;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountService {
    public boolean createAccount(AccountEntity acc);
    public List<AccountEntity> viewAccounts();
    public boolean updateAccount(AccountEntity account,String acc);
    public String deactivateAccount(String userName) throws AccountNotFoundException;
    public boolean assignRole(String userName, Role role) throws AccountNotFoundException;
}