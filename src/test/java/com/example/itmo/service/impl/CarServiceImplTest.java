package com.example.itmo.service.impl;

import com.example.itmo.exceptions.CustomException;
import com.example.itmo.model.db.entity.Car;
import com.example.itmo.model.db.entity.User;
import com.example.itmo.model.db.repository.CarRepo;
import com.example.itmo.model.dto.request.CarInfoRequest;
import com.example.itmo.model.dto.request.UserInfoRequest;
import com.example.itmo.model.dto.response.CarInfoResponse;
import com.example.itmo.model.dto.response.UserInfoResponse;
import com.example.itmo.model.enums.CarStatus;
import com.example.itmo.model.enums.Color;
import com.example.itmo.model.enums.UserStatus;
import com.example.itmo.service.CarService;
import com.example.itmo.service.UserService;
import com.example.itmo.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {
    @InjectMocks
    private CarServiceImpl carService;
    @Mock
    private CarRepo carRepo;
    @Mock
    private UserService userService;
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
        CarInfoRequest request = new CarInfoRequest();
        request.setYear(1997);

        Car car = new Car();
        car.setId(1L);
        car.setYear(2007);
        car.setColor(Color.GREEN);

        when(carRepo.findById(car.getId())).thenReturn(Optional.of(car));
        when(carRepo.save(any(Car.class))).thenReturn(car);

        CarInfoResponse result = carService.updateCar(car.getId(), request);
        assertEquals(car.getColor(), result.getColor());
        assertEquals(request.getYear(), result.getYear());
    }

    @Test
    public void deleteCar() {
        Car car = new Car();
        car.setId(1L);

        when(carRepo.findById(car.getId())).thenReturn(Optional.of(car));
        carService.deleteCar(car.getId());
        verify(carRepo, times(1)).save(any(Car.class));
        assertEquals(CarStatus.DELETED, car.getStatus());
    }

    @Test
    public void getAllCars() {
        List<Car> cars = new ArrayList<>();
        List<CarInfoResponse> carInfoResponses = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            Car car = new Car();
            car.setId(i);
            cars.add(car);
        }
        carInfoResponses = cars.stream().map(c -> mapper.convertValue(c, CarInfoResponse.class)).collect(Collectors.toList());
        when(carRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(cars));

        Page<CarInfoResponse> result = carService.getAllCars(1, 10, "id", Sort.Direction.ASC);
        assertEquals(result.getTotalElements(), cars.size());

        List<Long> ids = carInfoResponses.stream().map(CarInfoResponse::getId).collect(Collectors.toList());
        List<Long> resultIds = result.getContent().stream().map(CarInfoResponse::getId).collect(Collectors.toList());
        assertEquals(ids, resultIds);
    }

    @Test
    public void linkCarAndDriver() {
        User user = new User();
        user.setId(1L);
        user.setCars(new ArrayList<Car>());

        Car car = new Car();
        car.setId(1L);

        when(userService.getUserDb(1L)).thenReturn(user);
        when(carRepo.findById(car.getId())).thenReturn(Optional.of(car));

        when(userService.updateCarList(any(User.class))).thenReturn(user);

        when(carRepo.save(any(Car.class))).thenReturn(car);

        CarInfoResponse carInfoResponse = mapper.convertValue(car, CarInfoResponse.class);
        UserInfoResponse userInfoResponse = mapper.convertValue(user, UserInfoResponse.class);

        carInfoResponse.setUser(userInfoResponse);

        CarInfoResponse result = carService.linkCarAndDriver(1L, 1L);

        assertEquals(carInfoResponse.getId(), result.getId());
        assertEquals(carInfoResponse.getUser().getId(), result.getUser().getId());
    }

    @Test
    public void getUserCars() {
        User user = new User();
        user.setId(1L);
        UserInfoResponse userInfoResponse = mapper.convertValue(user, UserInfoResponse.class);

        when(userService.getUser(anyLong())).thenReturn(userInfoResponse);

        //Pageable request = PaginationUtil.getPageRequest(1, 2, "id", Sort.Direction.ASC);

        List<Car> cars = new ArrayList<>();
        for (long i = 0; i < 2; i++) {
            Car car = new Car();
            car.setId(i);
            cars.add(car);
        }
        List<CarInfoResponse> carInfoResponses = cars.stream().map(c -> mapper.convertValue(c, CarInfoResponse.class)).collect(Collectors.toList());
        when(carRepo.findAllByUserId(anyLong(), any(Pageable.class))).thenReturn(new PageImpl<>(cars));

        Page<CarInfoResponse> result = carService.getUserCars(1L, 1, 10, "id", Sort.Direction.ASC);
        assertEquals(result.getTotalElements(), cars.size());

        Page<CarInfoResponse> testPage = new PageImpl<>(carInfoResponses);

        List<Long> ids = carInfoResponses.stream().map(CarInfoResponse::getId).collect(Collectors.toList());
        List<Long> resultIds = result.getContent().stream().map(CarInfoResponse::getId).collect(Collectors.toList());
        assertEquals(ids, resultIds);
    }
}