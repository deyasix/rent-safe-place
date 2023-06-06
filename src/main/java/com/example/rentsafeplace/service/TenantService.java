package com.example.rentsafeplace.service;

import com.example.rentsafeplace.api.model.Building;
import com.example.rentsafeplace.api.model.Company;
import com.example.rentsafeplace.api.model.Realtor;
import com.example.rentsafeplace.api.model.Tenant;
import com.example.rentsafeplace.api.repository.BuildingRepository;
import com.example.rentsafeplace.api.repository.TenantRepository;
import com.example.rentsafeplace.api.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private RealtorService realtorService;

    @Autowired
    private BuildingService buildingService;

    public Tenant getTenant(Long id) {
        return tenantRepository.findById(id).orElse(null);
    }
    public ArrayList<Tenant> getAll() {
        return new ArrayList<>(tenantRepository.findAll());
    }
    public Tenant addTenant(Tenant tenant) {
        try {
            List<Realtor> realtors = realtorService.getAll();
            List<Company> companies = companyService.getAll();
            String email = tenant.getEmail();
            boolean isNameNotAvailable = realtors.stream().anyMatch(r -> r.getEmail().equals(email))
                    || companies.stream().anyMatch(c -> c.getEmail().equals(email) || getAll().stream()
                    .anyMatch(t -> t.getEmail().equals(email)));
            if (!isNameNotAvailable) {
                SecurityConfiguration.addTenantUser(tenant);
                return tenantRepository.save(tenant);
            } else return null;

        } catch (Exception exception) {
            return null;
        }
    }

    public Tenant updateTenant(Long id, Tenant tenant) {
        Optional<Tenant> tenantData = tenantRepository.findById(id);
        if (tenantData.isPresent()) {
            Tenant _tenant = tenantData.get();
            if (tenant.getName() != null) {
                _tenant.setName(tenant.getName());
            }
            if (tenant.getPassword() != null) {
                _tenant.setPassword(tenant.getPassword());
            }
            if (tenant.getEmail() != null) {
                _tenant.setEmail(tenant.getEmail());
            }
            if (tenant.getPhone() != null) {
                _tenant.setPhone(tenant.getPhone());
            }
            if (tenant.getPhoto() != null) {
                _tenant.setPhoto(tenant.getPhoto());
            }
            return tenantRepository.save(_tenant);
        } else {
            return null;
        }
    }

    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }

    public Tenant login(Tenant tenant) throws Exception {
        Tenant ten = getByEmail(tenant.getEmail());
        if (ten == null) throw new Exception("No such user!");
        if (ten.getPassword().equals(tenant.getPassword())) {
            return ten;
        } else throw new Exception("Wrong password!");
    }

    public Tenant getByEmail(String email) {
        List<Tenant> tenants = getAll();
        for (Tenant tenant : tenants) {
            if (tenant.getEmail().equals(email)) {
                return tenant;
            }
        }
        return null;
    }

    public Realtor getRealtors(Long id) {
        return realtorService.getRealtor(id);
    }

    public List<Building> getBuildings() {
        return buildingService.getAll();
    }

    public Building getBuilding(Long id) {
        return buildingService.getBuilding(id);
    }

    public Integer getStatistics(String city) {
        return buildingService.getStatistics(city);
    }
}
