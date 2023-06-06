package com.example.rentsafeplace.api.controller;

import com.example.rentsafeplace.api.model.Building;
import com.example.rentsafeplace.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("buildings")
public class BuildingController {

    private final BuildingService buildingService;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Building> getBuilding() {
        return new ArrayList<>(buildingService.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Building getBuilding(@PathVariable("id") Long id) {
        return buildingService.getBuilding(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Building addBuilding(@RequestBody Building building) {
        return buildingService.addBuilding(building);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Building updateBuilding(@PathVariable("id") Long id, @RequestBody Building building) {
        return buildingService.updateBuilding(id, building);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteBuilding(@PathVariable("id") Long id) {
        try {
            buildingService.deleteBuilding(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
