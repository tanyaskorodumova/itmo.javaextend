package com.example.itmo.service.impl;

import com.example.itmo.model.db.entity.User;
import com.example.itmo.model.db.repository.UserRepo;
import com.example.itmo.model.dto.request.UserInfoRequest;
import com.example.itmo.model.dto.response.CarInfoResponse;
import com.example.itmo.model.dto.response.UserInfoResponse;
import com.example.itmo.model.enums.UserStatus;
import com.example.itmo.service.UserService;
import com.example.itmo.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final ObjectMapper mapper;
    private final UserRepo userRepo;

    @Override
    public UserInfoResponse getUser(Long id) {
        return mapper.convertValue(getUserDb(id), UserInfoResponse.class);
    }

    @Override
    public User getUserDb(Long id) {
        return userRepo.findById(id).orElse(new User());
    }

    @Override
    public UserInfoResponse createUser(UserInfoRequest request) {
        User user = mapper.convertValue(request, User.class);
        user.setStatus(UserStatus.CREATED);
        user.setCreatedAt(LocalDateTime.now());
        user = userRepo.save(user);

        return mapper.convertValue(user, UserInfoResponse.class);
    }

    @Override
    public UserInfoResponse updateUser(Long id, UserInfoRequest request) {
        User user = getUserDb(id);
        if (user.getId() != null) {
            user.setEmail(request.getEmail() == null ? user.getEmail() : request.getEmail());
            user.setPassword(request.getPassword() == null ? user.getPassword() : request.getPassword());
            user.setFirstName(request.getFirstName() == null ? user.getFirstName() : request.getFirstName());
            user.setLastName(request.getLastName() == null ? user.getLastName() : request.getLastName());
            user.setMiddleName(request.getMiddleName() == null ? user.getMiddleName() : request.getMiddleName());
            user.setAge(request.getAge() == null ? user.getAge() : request.getAge());
            user.setGender(request.getGender() == null ? user.getGender() : request.getGender());

            user.setStatus(UserStatus.UPDATED);
            user.setUpdatedAt(LocalDateTime.now());

            user = userRepo.save(user);
        }
        else {
            log.error("User not found");
        }

        return mapper.convertValue(user, UserInfoResponse.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserDb(id);
        if (user.getId() != null) {
            user.setStatus(UserStatus.DELETED);
            user.setUpdatedAt(LocalDateTime.now());
            userRepo.save(user);
        }
        else {
            log.error("User not found");
        }
    }

    @Override
    public Page<UserInfoResponse> getAllUsers(Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);

        List<UserInfoResponse> all = userRepo.findAll(request)
                .getContent()
                .stream()
                .map(user -> mapper.convertValue(user, UserInfoResponse.class))
                .collect(Collectors.toList());

        Page<UserInfoResponse> pageResponse = new PageImpl<>(all);

        return pageResponse;
    }

    @Override
    public User updateCarList(User user) {
        return userRepo.save(user);
    }

    @Override
    public List<CarInfoResponse> getUserCars(Long id) {
        User user = getUserDb(id);
        return user.getCars()
                .stream()
                .map(car -> mapper.convertValue(car, CarInfoResponse.class))
                .collect(Collectors.toList());
    }
}
