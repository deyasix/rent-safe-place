package com.example.rentsafeplace.service;

import com.example.rentsafeplace.api.model.Building;
import com.example.rentsafeplace.api.model.Realtor;
import com.example.rentsafeplace.api.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    public Building getBuilding(Long id) {
        return buildingRepository.findById(id).orElse(null);
    }

    public ArrayList<Building> getAll() {
        try {
            return new ArrayList<>(buildingRepository.findAll());
        } catch (Exception exception) {
            return null;
        }

    }

    public Building addBuilding(Building building) {
        try {
            return buildingRepository.save(building);
        } catch (Exception exception) {
            return null;
        }
    }

    public Building updateBuilding(Long id, Building building) {
        Optional<Building> buildingData = buildingRepository.findById(id);
        if (buildingData.isPresent()) {
            RealtorService rs = new RealtorService();
            Building _building = buildingData.get();
            if (building.getCategory() != null) {
                _building.setCategory(building.getCategory());
            }
            if (building.getDescription() != null) {
                _building.setDescription(building.getDescription());
            }
            if (building.getIsLeased() != null) {
                _building.setIsLeased(building.getIsLeased());
            }
            if (building.getPhoto() != null) {
                _building.setPhoto(building.getPhoto());
            }
            if (building.getPrice() != null) {
                _building.setPrice(building.getPrice());
            }
            Realtor realtor = building.getRealtor();
            if (realtor != null && realtor.getId() != null) {
                _building.setRealtor(rs.getRealtor(realtor.getId()));
            }
            if (building.getType() != null) {
                _building.setType(building.getType());
            }
            if (building.getSquare() != null) {
                _building.setSquare(building.getSquare());
            }
            if (building.getIsPetAllowed() != null) {
                _building.setIsPetAllowed(building.getIsPetAllowed());
            }
            if (building.getName() != null) {
                _building.setName(building.getName());
            }
            if (building.getCity() != null) {
                _building.setCity(building.getCity());
            }
            if (building.getAddress() != null) {
                _building.setAddress(building.getAddress());
            }
            return buildingRepository.save(_building);
        } else {
            return null;
        }
    }

    public Integer getStatistics(String city) {
        List<Building> buildingsList = getAll();
        int sum = 0;
        int quantity = 0;
        for (Building building : buildingsList) {
            if (building.getCity().equals(city)) {
                quantity++;
                sum += building.getPrice();
            }
        }
        return sum / quantity;
    }

    public void deleteBuilding(Long id) {
        buildingRepository.deleteById(id);
    }
}
