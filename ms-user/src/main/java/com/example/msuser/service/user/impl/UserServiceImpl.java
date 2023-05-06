package com.example.msuser.service.user.impl;

import static com.example.msuser.util.enums.type.RoleType.USER;

import com.example.msuser.exception.UserAlreadyExistsException;
import com.example.msuser.model.request.RegisterUserReq;
import com.example.msuser.repository.entity.User;
import com.example.msuser.repository.UserRepository;
import com.example.msuser.service.role.RoleService;
import com.example.msuser.service.user.UserService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.valueOf(id)));
    }

    @Override
    public User findUserEntityByUsername(String username) {
        return userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public void registerUser(RegisterUserReq registerUserReq) {
        var role = roleService
                .findByRoleType(USER);

        userRepository
                .findByUsernameIgnoreCase(registerUserReq.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(registerUserReq.getUsername());
                });

        userRepository
                .save(User
                        .builder()
                        .username(registerUserReq.getUsername())
                        .password(encoder.encode(registerUserReq.getPassword()))
                        .roles(Collections.singleton(role))
                        .build());
        log.info("Registered user account for: {}", registerUserReq.getUsername());
    }
}
