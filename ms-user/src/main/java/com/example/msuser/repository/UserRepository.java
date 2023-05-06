package com.example.msuser.repository;

import com.example.msuser.repository.entity.Role;
import com.example.msuser.repository.entity.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String username);

    Page<User> findAllByRolesIn(Pageable pageable, Set<Role> roles);
}
