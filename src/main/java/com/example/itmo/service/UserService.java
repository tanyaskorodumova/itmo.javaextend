package com.example.itmo.service;

import com.example.itmo.model.db.entity.User;
import com.example.itmo.model.dto.request.UserInfoRequest;
import com.example.itmo.model.dto.response.CarInfoResponse;
import com.example.itmo.model.dto.response.UserInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface UserService {
    UserInfoResponse getUser(Long id);

    User getUserDb(Long id);

    UserInfoResponse createUser(UserInfoRequest request);

    UserInfoResponse updateUser(Long id, UserInfoRequest request);

    void deleteUser(Long id);

    Page<UserInfoResponse> getAllUsers(Integer page, Integer perPage, String sort, Sort.Direction order);

    User updateCarList(User user);

    Page<CarInfoResponse> getUserCars(Long id, Integer page, Integer perPage, String sort, Sort.Direction order);
}
