package com.example.rentsafeplace.api.repository;

import com.example.rentsafeplace.api.model.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealtorRepository extends JpaRepository<Realtor, Long> {
}
