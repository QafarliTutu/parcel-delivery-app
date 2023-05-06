package com.example.msuser.service.courier.impl;

import static com.example.msuser.util.enums.type.RoleType.COURIER;

import com.example.msuser.exception.UserAlreadyExistsException;
import com.example.msuser.model.request.CreateCourierReq;
import com.example.msuser.model.request.RegisterCourierReq;
import com.example.msuser.rabbit.RabbitMessage;
import com.example.msuser.rabbit.RabbitProducerService;
import com.example.msuser.repository.UserRepository;
import com.example.msuser.repository.entity.User;
import com.example.msuser.service.courier.CourierService;
import com.example.msuser.service.role.RoleService;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    private final RabbitProducerService producerService;

    @Override
    public void registerCourier(RegisterCourierReq registerCourierReq) {
        var role = roleService
                .findByRoleType(COURIER);

        userRepository
                .findByUsernameIgnoreCase(registerCourierReq.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(registerCourierReq.getUsername());
                });

        var savedUser = userRepository.save(User
                .builder()
                .username(registerCourierReq.getUsername())
                .password(encoder.encode(registerCourierReq.getTemporaryPassword()))
                .roles(Collections.singleton(role))
                .build());
        log.info("Registered courier account for: {}", savedUser.getUsername());

        producerService.sendMessage(RabbitMessage
                .builder()
                .operation(RabbitMessage.Operation.CREATE_COURIER)
                .payload(CreateCourierReq
                        .builder()
                        .id(savedUser.getId())
                        .username(savedUser.getUsername())
                        .isActive(savedUser.getIsActive())
                        .courierType(registerCourierReq.getCourierType())
                        .build())
                .build());
    }
}
