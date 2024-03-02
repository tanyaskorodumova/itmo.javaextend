package com.example.itmo.service.impl;

import com.example.itmo.exceptions.CustomException;
import com.example.itmo.model.db.entity.Car;
import com.example.itmo.model.db.repository.CarRepo;
import com.example.itmo.model.dto.request.CarInfoRequest;
import com.example.itmo.model.dto.response.CarInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {
    @InjectMocks
    private CarServiceImpl carService;
    @Mock
    private CarRepo carRepo;
    @Spy
    private ObjectMapper mapper;

    @Test
    public void createCar() {
        CarInfoRequest request = new CarInfoRequest();
        request.setBrand("Nissan");

        Car car = new Car();
        car.setId(1L);

        when(carRepo.save(any(Car.class))).thenReturn(car);
        CarInfoResponse result = carService.createCar(request);
        assertEquals(Long.valueOf(1L), result.getId());
    }

//    @Test(expected = MethodArgumentNotValidException.class)
//    public void createCarWithoutBrand() {
//        CarInfoRequest request = new CarInfoRequest();
//        carService.createCar(request);
//    }

    @Test(expected = CustomException.class)
    public void createCarInvalidYear() {
        CarInfoRequest request = new CarInfoRequest();
        request.setBrand("Nissan");
        request.setYear(2028);
        carService.createCar(request);
    }

    @Test
    public void getCar() {
    }

    @Test
    public void updateCar() {
    }

    @Test
    public void deleteCar() {
    }

    @Test
    public void getAllCars() {
    }

    @Test
    public void linkCarAndDriver() {
    }
}