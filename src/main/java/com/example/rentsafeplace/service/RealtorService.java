package com.example.rentsafeplace.service;

import com.example.rentsafeplace.api.model.Building;
import com.example.rentsafeplace.api.model.Company;
import com.example.rentsafeplace.api.model.Realtor;
import com.example.rentsafeplace.api.model.Tenant;
import com.example.rentsafeplace.api.repository.RealtorRepository;
import com.example.rentsafeplace.api.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RealtorService {
    @Autowired
    private RealtorRepository realtorRepository;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private CompanyService companyService;

    public Realtor getRealtor(Long id) {
        return realtorRepository.findById(id).orElse(null);
    }
    public ArrayList<Realtor> getAll() {
        return new ArrayList<>(realtorRepository.findAll());
    }

    public Realtor addRealtor(Realtor realtor) {
        try {
            List<Tenant> tenants = tenantService.getAll();
            List<Company> companies = companyService.getAll();
            String email = realtor.getEmail();
            boolean isNameNotAvailable = tenants.stream().anyMatch(t -> t.getEmail().equals(email))
                    || companies.stream().anyMatch(c -> c.getEmail().equals(email) || getAll().stream()
                    .anyMatch(r -> r.getEmail().equals(email)));
            if (!isNameNotAvailable) {
                SecurityConfiguration.addRealtorUser(realtor);
                return realtorRepository.save(realtor);
            } else return null;

        } catch (Exception exception) {
            return null;
        }
    }

    public Realtor updateRealtor(Long id, Realtor realtor) {
        Optional<Realtor> realtorData = realtorRepository.findById(id);
        if (realtorData.isPresent()) {
            Realtor _realtor = realtorData.get();
            if (realtor.getName() != null) {
                _realtor.setName(realtor.getName());
            }
            if (realtor.getPassword() != null) {
                _realtor.setPassword(realtor.getPassword());
            }
            if (realtor.getEmail() != null) {
                _realtor.setEmail(realtor.getEmail());
            }
            if (realtor.getPhone() != null) {
                _realtor.setPhone(realtor.getPhone());
            }
            if (realtor.getPhoto() != null) {
                _realtor.setPhoto(realtor.getPhoto());
            }
            return realtorRepository.save(_realtor);
        } else {
            return null;
        }
    }

    public void deleteRealtor(Long id) {
        realtorRepository.deleteById(id);
    }

    public Realtor login(Realtor realtor) throws Exception {
        Realtor real = getByEmail(realtor.getEmail());
        if (real == null) throw new Exception("No such user!");
        if (real.getPassword().equals(realtor.getPassword())) {
            return real;
        } else throw new Exception("Wrong password!");
    }

    public Realtor getByEmail(String email) {
        List<Realtor> realtors = getAll();
        for (Realtor realtor : realtors) {
            if (realtor.getEmail().equals(email)) {
                return realtor;
            }
        }
        return null;
    }

    public Company getCompany(Realtor realtor) {
        return realtor.getCompany();
    }

    public Tenant getTenantById(Long id) {
        return tenantService.getTenant(id);
    }

    public Building addBuilding(Building building) {
        return buildingService.addBuilding(building);
    }

    public List<Building> getBuildings(Realtor realtor) {
        List<Building> buildings = buildingService.getAll();
        List<Building> realtorBuilding = new ArrayList<>();
        for (Building building : buildings) {
            if (building.getRealtor().equals(realtor)) {
                realtorBuilding.add(building);
            }
        }
        return realtorBuilding;
    }

    public Building getBuilding(Long id, Realtor realtor) {
        Building building = buildingService.getBuilding(id);
        if (building.getRealtor().equals(realtor)) {
            return building;
        }
        return null;
    }

    public Building editBuilding(Long id, Building building, Realtor realtor) {
        Building build = buildingService.getBuilding(id);
        if (build.getRealtor().equals(realtor)) {
            return buildingService.updateBuilding(id, building);
        }
        return null;
    }

    public void deleteBuilding(Long id, Realtor realtor) {
        Building building = buildingService.getBuilding(id);
        if (building.getRealtor().equals(realtor)) {
            buildingService.deleteBuilding(id);
        }
    }
}
