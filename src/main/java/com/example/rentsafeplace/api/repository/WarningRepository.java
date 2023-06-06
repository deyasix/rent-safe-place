package com.example.rentsafeplace.api.repository;

import com.example.rentsafeplace.api.model.Warning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarningRepository extends JpaRepository<Warning, Long> {
}
