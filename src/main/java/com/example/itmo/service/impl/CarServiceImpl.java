package com.example.itmo.service.impl;

import com.example.itmo.model.dto.request.CarInfoRequest;
import com.example.itmo.model.dto.response.CarInfoResponse;
import com.example.itmo.service.CarService;
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
public class CarServiceImpl implements CarService {

    private List<CarInfoResponse> cars = new ArrayList<>();
    private final ObjectMapper mapper;
    private Long id = 1L;

    @Override
    public CarInfoResponse createCar(CarInfoRequest request) {
        CarInfoResponse car = mapper.convertValue(request, CarInfoResponse.class);
        car.setId(id++);
        cars.add(car);
        return car;
    }

    @Override
    public CarInfoResponse getCar(Long id) {
        List<CarInfoResponse> all = cars.stream().filter(c -> c.getId().equals(id))
                .collect(Collectors.toList());

        CarInfoResponse car = null;
        if (CollectionUtils.isEmpty(all)) {
            log.error(String.format("Car with id:%s is not found", id));
            return car;
        }

        car = all.get(0);
        return car;
    }

    @Override
    public CarInfoResponse updateCar(Long id, CarInfoRequest request) {
        CarInfoResponse car = getCar(id);

        if (Objects.isNull(car)) {
            log.error(String.format("Car's not updated"));
            return car;
        }

        CarInfoResponse response = mapper.convertValue(request, CarInfoResponse.class);
        response.setId(car.getId());

        return response;
    }

    @Override
    public void deleteCar(Long id) {
        CarInfoResponse car = getCar(id);

        if (Objects.isNull(car)) {
            log.error(String.format("Car's not deleted"));
            return;
        }

        cars.remove(car);
    }

    @Override
    public List<CarInfoResponse> getAllCars() {
        return cars;
    }
}
