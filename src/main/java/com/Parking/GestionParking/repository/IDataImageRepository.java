package com.Parking.GestionParking.repository;

import com.Parking.GestionParking.entities.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDataImageRepository extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String fileName);
}
