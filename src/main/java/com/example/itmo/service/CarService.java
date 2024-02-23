package com.example.itmo.service;

import com.example.itmo.model.dto.request.CarInfoRequest;
import com.example.itmo.model.dto.response.CarInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface CarService {
    CarInfoResponse createCar(CarInfoRequest request);

    CarInfoResponse getCar(Long id);

    CarInfoResponse updateCar(Long id, CarInfoRequest request);

    void deleteCar(Long id);

    Page<CarInfoResponse> getAllCars(Integer page, Integer perPage, String sort, Sort.Direction order);

    CarInfoResponse linkCarAndDriver(Long userId, Long carId);
}
