package com.his.admin.dto;

import com.his.admin.entity.Role;
import lombok.Data;

@Data
public class RoleUpdateRequest {
    private String userName;
    private Role role;
}
