package com.example.itmo.controllers;

import com.example.itmo.model.dto.request.CarInfoRequest;
import com.example.itmo.model.dto.response.CarInfoResponse;
import com.example.itmo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    public CarInfoResponse createCar(@RequestBody CarInfoRequest request) {
        return carService.createCar(request);
    }

    @GetMapping("/{id}")
    public CarInfoResponse getCar(@PathVariable Long id) {
        return carService.getCar(id);
    }

    @PutMapping("/{id}")
    public CarInfoResponse updateCar(@PathVariable Long id, @RequestBody CarInfoRequest request) {
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @GetMapping("/all")
    public Page<CarInfoResponse> getAllCars(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer perPage,
                                            @RequestParam(defaultValue = "brand") String sort,
                                            @RequestParam(defaultValue = "ASC") Sort.Direction order)
    {
        return carService.getAllCars(page, perPage, sort, order);
    }

    @PostMapping("/linkCarAndDriver/{userId}/{carId}")
    public CarInfoResponse linkCarAndDriver(@PathVariable Long userId, @PathVariable Long carId) {
        return carService.linkCarAndDriver(userId, carId);
    }
}
