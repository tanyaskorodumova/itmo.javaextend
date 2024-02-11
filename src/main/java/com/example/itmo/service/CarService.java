package com.example.itmo.service;

import com.example.itmo.model.dto.request.CarInfoRequest;
import com.example.itmo.model.dto.response.CarInfoResponse;

import java.util.List;

public interface CarService {
    CarInfoResponse createCar(CarInfoRequest request);

    CarInfoResponse getCar(Long id);

    CarInfoResponse updateCar(Long id, CarInfoRequest request);

    void deleteCar(Long id);

    List<CarInfoResponse> getAllCars();

}
