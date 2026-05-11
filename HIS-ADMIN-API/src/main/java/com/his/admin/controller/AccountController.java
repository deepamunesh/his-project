package com.his.admin.controller;

import com.his.admin.entity.AccountEntity;
import com.his.admin.entity.Role;
import com.his.admin.dto.RoleUpdateRequest;
import com.his.admin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
 @RequestMapping("/account")

public class AccountController {
@Autowired
private AccountService service;


 @PostMapping("/create")
 public ResponseEntity<String> createAccount(
         @RequestBody AccountEntity acc) {

     boolean status = service.createAccount(acc);
     if (status) {
         return new ResponseEntity < > (
                 "Account Created",
         HttpStatus.CREATED);
     }
     return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
 }

 @GetMapping("/all")
public List<AccountEntity> viewAccounts() {
     return service.viewAccounts();
 }

 @PutMapping("/update/{ssn}")
public ResponseEntity<String>updateAccount( @RequestBody AccountEntity account, @PathVariable String ssn) {
     boolean status = service.updateAccount(account,ssn);
     if (status) {
         return ResponseEntity.ok("Updated");
     }
     return ResponseEntity.badRequest()
             .body("Not Found");
 }


    @PutMapping("/deactivate/{userName}")
public ResponseEntity<String> deactivateAccount(@PathVariable String userName) throws AccountNotFoundException {

    String response = service.deactivateAccount(userName);
    return ResponseEntity.ok(response);
}

    @PutMapping("/assign-role")
    public ResponseEntity<String> assignRole(@RequestBody RoleUpdateRequest request) throws AccountNotFoundException {
        boolean updated = service.assignRole(request.getUserName(), request.getRole());
        if (updated) {
            return ResponseEntity.ok("Role updated successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update role");
    }

 }
