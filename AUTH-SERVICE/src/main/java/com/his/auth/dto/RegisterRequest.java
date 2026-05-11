package com.his.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // e.g. ADMIN, SUPER_ADMIN, CASE_WORKER
}