package com.his.dc.service;

import com.his.dc.entity.AccountEntity;
import com.his.dc.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DcUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public DcUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity account = accountRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.withUsername(account.getUserName())
                .password(account.getPassword()) // BCrypt hash from DB
                .roles(account.getRole().name()) // CASE_WORKER / ADMIN
                .build();
    }
}
