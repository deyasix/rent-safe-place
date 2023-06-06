package com.example.rentsafeplace.api.controller;

import com.example.rentsafeplace.api.model.Building;
import com.example.rentsafeplace.api.model.Realtor;
import com.example.rentsafeplace.api.model.Tenant;
import com.example.rentsafeplace.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("tenants")
public class TenantController {


    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tenant> getTenants() {
        return new ArrayList<>(tenantService.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tenant getTenant(@PathVariable("id") Long id) {
        Tenant tenant = tenantService.getTenant(id);
        if (tenant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return tenant;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tenant addTenant(@RequestBody Tenant tenant) {
        Tenant tenantResult = tenantService.addTenant(tenant);
        if (tenantResult == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return tenantService.addTenant(tenant);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tenant updateTenant(@PathVariable("id") Long id, @RequestBody Tenant tenant) {
        return tenantService.updateTenant(id, tenant);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteTenant(@PathVariable("id") Long id) {
        try {
            tenantService.deleteTenant(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tenant registerTenant(@RequestBody Tenant tenant) {
        return tenantService.addTenant(tenant);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tenant loginTenant(@RequestBody Tenant tenant) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(tenant.getEmail(), tenant.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return tenantService.login(tenant);
        } catch (Exception exception) {
            return null;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus logoutTenant() {
        try {
            SecurityContextHolder.clearContext();
            return HttpStatus.NO_CONTENT;
        } catch (Exception exception) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/realtors/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Realtor getRealtors(@PathVariable("id") Long id) {
        return tenantService.getRealtors(id);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tenant getInfo() {
        return getAuthTenant();
    }

    @RequestMapping(value = "/info", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Tenant updateInfo(@RequestBody Tenant tenant) {
        Long id = getAuthTenant().getId();
        return tenantService.updateTenant(id, tenant);
    }

    @RequestMapping(value = "/info", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteAccount() {
        try {
            Long id = getAuthTenant().getId();
            tenantService.deleteTenant(id);
            return logoutTenant();
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/buildings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Building> getBuildings() {
        return tenantService.getBuildings();
    }

    @RequestMapping(value = "/buildings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Building getBuilding(@PathVariable("id") Long id) {
        return tenantService.getBuilding(id);
    }

    @RequestMapping(value = "/buildings/statistics/{city}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getStatistics(@PathVariable("city") String city) {
        return tenantService.getStatistics(city);
    }

    private Tenant getAuthTenant() {
        String tenantEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return tenantService.getByEmail(tenantEmail);
    }

}
