package com.example.rentsafeplace.api.controller;

import com.example.rentsafeplace.api.model.Building;
import com.example.rentsafeplace.api.model.Company;
import com.example.rentsafeplace.api.model.Realtor;
import com.example.rentsafeplace.api.model.Tenant;
import com.example.rentsafeplace.service.RealtorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("realtors")
public class RealtorController {

    private final RealtorService realtorService;

    @Autowired
    public RealtorController(RealtorService realtorService) {
        this.realtorService = realtorService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Realtor> getRealtors() {
        return new ArrayList<>(realtorService.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Realtor getRealtor(@PathVariable("id") Long id) {
        return realtorService.getRealtor(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Realtor addRealtor(@RequestBody Realtor realtor) {
        return realtorService.addRealtor(realtor);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Realtor updateRealtor(@PathVariable("id") Long id, @RequestBody Realtor realtor) {
        return realtorService.updateRealtor(id, realtor);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteRealtor(@PathVariable("id") Long id) {
        try {
            realtorService.deleteRealtor(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Realtor loginRealtor(@RequestBody Realtor realtor) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(realtor.getEmail(), realtor.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return realtorService.login(realtor);
        } catch (Exception exception) {
            return null;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus logoutRealtor() {
        try {
            SecurityContextHolder.clearContext();
            return HttpStatus.NO_CONTENT;
        } catch (Exception exception) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/company", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company getCompany() {
        return realtorService.getCompany(getAuthRealtor());
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Realtor getInfo() {
        return getAuthRealtor();
    }

    @RequestMapping(value = "/info", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Realtor updateInfo(@RequestBody Realtor realtor) {
        Long id = getAuthRealtor().getId();
        return realtorService.updateRealtor(id, realtor);
    }

    @RequestMapping(value = "/tenants/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tenant getTenant(@PathVariable("id") Long id) {
        return realtorService.getTenantById(id);
    }

    @RequestMapping(value = "/buildings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Building addBuilding(@RequestBody Building building) {
        building.setRealtor(getAuthRealtor());
        return realtorService.addBuilding(building);
    }

    @RequestMapping(value = "/buildings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Building> getBuildings() {
        return realtorService.getBuildings(getAuthRealtor());
    }

    @RequestMapping(value = "/buildings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Building getBuilding(@PathVariable("id") Long id) {
        return realtorService.getBuilding(id, getAuthRealtor());
    }

    @RequestMapping(value = "/buildings/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Building editBuilding(@PathVariable("id") Long id, @RequestBody Building building) {
        return realtorService.editBuilding(id, building, getAuthRealtor());
    }

    @RequestMapping(value = "/buildings/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteBuilding(@PathVariable("id") Long id) {
        try {
            realtorService.deleteBuilding(id, getAuthRealtor());
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private Realtor getAuthRealtor() {
        String realtorEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return realtorService.getByEmail(realtorEmail);
    }
}
