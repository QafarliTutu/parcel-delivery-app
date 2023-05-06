package com.example.msuser.service.role.impl;

import com.example.msuser.exception.RoleNotFoundException;
import com.example.msuser.repository.RoleRepository;
import com.example.msuser.repository.entity.Role;
import com.example.msuser.service.role.RoleService;
import com.example.msuser.util.enums.type.RoleType;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByRoleType(RoleType roleType) {
        return roleRepository
                .findByType(roleType)
                .orElseThrow(() -> new RoleNotFoundException(roleType));
    }

    @Override
    public Set<Role> findAllByRoleType(Set<RoleType> roleTypes) {
        return roleRepository
                .findAllByTypeIn(roleTypes);
    }
}
