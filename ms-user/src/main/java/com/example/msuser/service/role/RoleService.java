package com.example.msuser.service.role;

import com.example.msuser.repository.entity.Role;
import com.example.msuser.util.enums.type.RoleType;
import java.util.Set;

public interface RoleService {

    Role findByRoleType(RoleType roleType);

    Set<Role> findAllByRoleType(Set<RoleType> roleTypes);
}
