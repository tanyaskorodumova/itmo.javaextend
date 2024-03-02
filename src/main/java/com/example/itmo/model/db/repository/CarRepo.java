package com.example.itmo.model.db.repository;

import com.example.itmo.model.db.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {

    @Query("select c from Car c where c.user.firstName = :userFirstName")
    List<Car> findCars(@Param("userFirstName") String userFirstName);

    Page<Car> findAllByUserId(Long userId, Pageable pageable);

}
