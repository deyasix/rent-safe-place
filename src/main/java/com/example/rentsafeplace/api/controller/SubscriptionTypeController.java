package com.example.rentsafeplace.api.controller;

import com.example.rentsafeplace.api.model.SubscriptionType;
import com.example.rentsafeplace.service.SubscriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("subscriptionTypes")
public class SubscriptionTypeController {

    private final SubscriptionTypeService subscriptionTypeService;

    @Autowired
    public SubscriptionTypeController(SubscriptionTypeService subscriptionTypeService) {
        this.subscriptionTypeService = subscriptionTypeService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubscriptionType> getSubscriptionTypes() {
        return new ArrayList<>(subscriptionTypeService.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SubscriptionType getSubscriptionType(@PathVariable("id") Long id) {
        return subscriptionTypeService.getSubscriptionType(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SubscriptionType addSubscriptionType(@RequestBody SubscriptionType subscriptionType) {
        return subscriptionTypeService.addSubscriptionType(subscriptionType);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public SubscriptionType updateSubscriptionType(@PathVariable("id") Long id, @RequestBody SubscriptionType subscriptionType) {
        return subscriptionTypeService.updateSubscriptionType(id, subscriptionType);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteSubscriptionType(@PathVariable("id") Long id) {
        try {
            subscriptionTypeService.deleteSubscriptionType(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
