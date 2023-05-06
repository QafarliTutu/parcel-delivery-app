package com.example.msuser.repository;

import com.example.msuser.repository.entity.Role;
import com.example.msuser.util.enums.type.RoleType;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Short> {

    Optional<Role> findByType(RoleType roleType);

    Set<Role> findAllByTypeIn(Set<RoleType> roleTypes);

}
