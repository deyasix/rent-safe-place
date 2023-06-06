package com.example.rentsafeplace.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rentsafeplace.api.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> { }
