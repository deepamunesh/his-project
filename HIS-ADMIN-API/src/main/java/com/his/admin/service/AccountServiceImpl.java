package com.his.admin.service;

import com.his.admin.exception.AccountAlreadyExistsException;
import com.his.admin.exception.UsernameAlreadyExistsException;
import com.his.admin.entity.AccountEntity;
import com.his.admin.entity.Role;
import com.his.admin.exception.AccountNotFoundException;
import com.his.admin.exception.AccessDeniedCustomException;
import com.his.admin.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    // Constants for active/inactive flags and system user
    private static final String ACTIVE_FLAG = "Y";   // Account active flag
    private static final String INACTIVE_FLAG = "N"; // Account inactive flag
    private static final String SYSTEM_USER = "SYSTEM"; // Default user if no authentication context
    @Autowired
    private AccountRepository repo;  // Repository for DB operations on AccountEntity

    @Autowired
    private PasswordEncoder passwordEncoder; // For encoding passwords securely
    // Create a new account
    public boolean createAccount(AccountEntity acc) {
        // Get current logged-in user's role
        String currentRole = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        // Business rule: Only ADMIN can create accounts
        if (!"ROLE_ADMIN".equals(currentRole)) {
            throw new AccessDeniedCustomException("You don’t have access to create accounts");
        }



        // Normalize input (trim + lowercase)
        String email = acc.getEmail().trim().toLowerCase();
        String username = acc.getUserName().trim().toLowerCase();
        // Check if account already exists by email or username
        String firstName = acc.getFName();
        String lastName = acc.getLName();
        Optional<AccountEntity> existAcc= repo.findByEmail(email);
        Optional<AccountEntity> existUsername = repo.findByUserName(username);

        if (existAcc.isPresent()) {
            throw new AccountAlreadyExistsException("Account already exists");
        }
        if (existUsername.isPresent()) {
            throw new UsernameAlreadyExistsException("Account with this username already exists");
        }

        // Set normalized values back
        acc.setEmail(email);
        acc.setUserName(username);
        acc.setFName(firstName);
        acc.setLName(lastName);

        // Default flags and audit info
        acc.setActiveSw(ACTIVE_FLAG); // Mark account as active
        acc.setCreatedDt(LocalDateTime.now()); // Creation timestamp

        String currentUser = getCurrentUsername(); // Logged-in user
        acc.setCreatedBy(currentUser);
        acc.setUpdatedBy(currentUser);
        // Encode password before saving
        acc.setPassword(passwordEncoder.encode(acc.getPassword()));
        // If no role provided, default to CASE_WORKER
        if (acc.getRole() == null) {
        acc.setRole(Role.CASE_WORKER);
    }

        acc.setUpdatedDt(LocalDateTime.now()); // Update timestamp

        // Save account in DB
        repo.save(acc);
        return true;
    }

    // View all accounts (accessible to multiple roles)
    public List<AccountEntity> viewAccounts() {
        List<AccountEntity> accountEntity=repo.findAll();
        return accountEntity;
    }
    // Update account details by SSN
    public boolean updateAccount(AccountEntity account,String ssn) {
      AccountEntity accountDetails = repo.findBySsn(ssn);
    // Update phone number (example field)
      String activephno=account.getPhno();
      accountDetails.setPhno(activephno);
        if (accountDetails!=null) {
            repo.save(accountDetails);
            return true;
        }
        return false;
    }
    // Deactivate account by username
    public String deactivateAccount(String userName) {

    AccountEntity account = repo.findByUserName(userName)
            .orElseThrow(() -> new AccountNotFoundException(userName));

    String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

    String rol = SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .iterator()
            .next()
            .getAuthority();

    Role loggedInRole = Role.valueOf(rol.replace("ROLE_", ""));

        // BUSINESS RULES:
        // Case Worker cannot deactivate accounts
    if (loggedInRole == Role.CASE_WORKER) {
        throw new AccessDeniedCustomException("Not allowed");
    }
    // Admin cannot deactivate another Admin
    if (loggedInRole == Role.ADMIN && account.getRole() == Role.ADMIN) {
        throw new AccessDeniedCustomException("ADMIN cannot deactivate ADMIN");
    }
// Mark account inactive and update audit info
    account.setActiveSw("N");
    account.setUpdatedBy(loggedInUser);
    account.setUpdatedDt(LocalDateTime.now());

    repo.save(account);

    return "Account deactivated";
}

    // Assign role to a user (only Super Admin allowed in SecurityConfig)
    public boolean assignRole(String userName, Role role) throws AccountNotFoundException {
        AccountEntity account = repo.findByUserName(userName)
                .orElseThrow(() -> new AccountNotFoundException(userName));

        account.setRole(role);

        String loggedInUser=getCurrentUsername();
        account.setUpdatedBy(loggedInUser);

        LocalDateTime currentDate=LocalDateTime.now();
        account.setUpdatedDt(currentDate);

        repo.save(account);

        return true;
    }

    // Utility method to get current logged-in username
    private String getCurrentUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null || auth.getName().equals("anonymousUser")) {
            return SYSTEM_USER;
        }
        return auth.getName();
    }

}


