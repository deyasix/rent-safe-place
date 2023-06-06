package com.example.rentsafeplace.api.repository;

import com.example.rentsafeplace.api.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TenantRepository extends JpaRepository<Tenant, Long> {

}
