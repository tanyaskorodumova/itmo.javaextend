package com.example.itmo.service.impl;

import com.example.itmo.model.dto.request.UserInfoRequest;
import com.example.itmo.model.dto.response.UserInfoResponse;
import com.example.itmo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private List<UserInfoResponse> users = new ArrayList<>();
    private final ObjectMapper mapper;
    private Long id = 1L;

    @Override
    public UserInfoResponse getUser(Long id) {
        List<UserInfoResponse> all = users.stream().filter(u -> u.getId().equals(id))
                .collect(Collectors.toList());

        UserInfoResponse user = null;
        if (CollectionUtils.isEmpty(all)) {
            log.error(String.format("User with id:%s not found", id));
            return user;
        }

        user = all.get(0);
        return user;
    }

    @Override
    public UserInfoResponse createUser(UserInfoRequest request) {
        UserInfoResponse user = mapper.convertValue(request, UserInfoResponse.class);
        user.setId(id++);
        users.add(user);
        return user;
    }

    @Override
    public UserInfoResponse updateUser(Long id, UserInfoRequest request) {
        UserInfoResponse user = getUser(id);

        if (Objects.isNull(user)) {
            log.error(String.format("User's not updated"));
            return user;
        }

        UserInfoResponse response = mapper.convertValue(request, UserInfoResponse.class);
        response.setId(user.getId());

        return response;
    }

    @Override
    public void deleteUser(Long id) {
        UserInfoResponse user = getUser(id);

        if (Objects.isNull(user)) {
            log.error(String.format("User's not deleted"));
            return;
        }

        users.remove(user);
    }

    @Override
    public List<UserInfoResponse> getAllUsers() {
        return users;
    }
}
