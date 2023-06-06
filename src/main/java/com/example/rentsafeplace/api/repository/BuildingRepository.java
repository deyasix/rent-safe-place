package com.example.rentsafeplace.api.repository;

import com.example.rentsafeplace.api.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
