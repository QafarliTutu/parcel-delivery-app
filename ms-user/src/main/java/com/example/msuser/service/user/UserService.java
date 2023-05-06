package com.example.msuser.service.user;

import com.example.msuser.model.request.RegisterUserReq;
import com.example.msuser.repository.entity.User;

public interface UserService {

    User findById(Long id);

    User findUserEntityByUsername(String username);

    void registerUser(RegisterUserReq registerUserReq);
}
