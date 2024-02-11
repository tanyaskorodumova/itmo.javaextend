package com.example.itmo.service;

import com.example.itmo.model.dto.request.UserInfoRequest;
import com.example.itmo.model.dto.response.UserInfoResponse;

import java.util.List;

public interface UserService {
    UserInfoResponse getUser(Long id);

    UserInfoResponse createUser(UserInfoRequest request);

    UserInfoResponse updateUser(Long id, UserInfoRequest request);

    void deleteUser(Long id);

    List<UserInfoResponse> getAllUsers();
}
