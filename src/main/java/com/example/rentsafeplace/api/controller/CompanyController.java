package com.example.rentsafeplace.api.controller;

import com.example.rentsafeplace.api.model.Company;
import com.example.rentsafeplace.api.model.Realtor;
import com.example.rentsafeplace.api.model.Subscription;
import com.example.rentsafeplace.service.CompanyService;
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
@RequestMapping("companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Company> getCompanies() {
        return new ArrayList<>(companyService.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company getCompany(@PathVariable("id") Long id) {
        return companyService.getCompany(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company updateCompany(@PathVariable("id") Long id, @RequestBody Company company) {
        return companyService.updateCompany(id, company);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteCompany(@PathVariable("id") Long id) {
        try {
            companyService.deleteCompany(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company registerCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company loginCompany(@RequestBody Company company) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(company.getEmail(), company.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return companyService.login(company);
        } catch (Exception exception) {
            return null;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus logoutCompany() {
        try {
            SecurityContextHolder.clearContext();
            return HttpStatus.NO_CONTENT;
        } catch (Exception exception) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company getInfo() {
        return getAuthCompany();
    }

    @RequestMapping(value = "/info", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company updateInfo(@RequestBody Company company) {
        Long id = getAuthCompany().getId();
        return companyService.updateCompany(id, company);
    }

    @RequestMapping(value = "/info", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteAccount() {
        try {
            Long id = getAuthCompany().getId();
            companyService.deleteCompany(id);
            return logoutCompany();
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/subscription", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Subscription getSubscription() {
        try {
            return companyService.getSubscription(getAuthCompany());
        } catch (Exception exception) {
            return null;
        }
    }

    @RequestMapping(value = "/subscription", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Subscription subscribe(@RequestBody Subscription subscription) {
        subscription.setCompany(getAuthCompany());
        return companyService.subscribe(subscription);
    }

    @RequestMapping(value = "/subscription", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Subscription editSubscription(@RequestBody Subscription subscription) {
        subscription.setCompany(getAuthCompany());
        return companyService.editSubscription(subscription);
    }

    @RequestMapping(value = "/subscription", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus cancelSubscription() {
        try {
            companyService.cancelSubscription(getAuthCompany());
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping(value = "/realtors", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Realtor createRealtor(@RequestBody Realtor realtor) {
        realtor.setCompany(getAuthCompany());
        return companyService.createRealtor(realtor);
    }

    @RequestMapping(value = "/realtors/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Realtor editRealtor(@PathVariable("id") Long id, @RequestBody Realtor realtor) {
        return companyService.editRealtor(id, realtor, getAuthCompany());
    }

    @RequestMapping(value = "/realtors", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Realtor> getRealtors() {
        return companyService.getRealtors(getAuthCompany());
    }

    @RequestMapping(value = "/realtors/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Realtor getRealtor(@PathVariable("id") Long id) {
        return companyService.getRealtor(id, getAuthCompany());
    }

    @RequestMapping(value = "/realtors/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteRealtor(@PathVariable("id") Long id) {
        try {
            companyService.deleteRealtor(id, getAuthCompany());
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private Company getAuthCompany() {
        String companyEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return companyService.getByEmail(companyEmail);
    }

}
