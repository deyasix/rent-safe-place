package com.example.rentsafeplace.api.controller;

import com.example.rentsafeplace.api.model.Warning;
import com.example.rentsafeplace.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("warnings")
public class WarningController {

    private final WarningService warningService;

    @Autowired
    public WarningController(WarningService warningService) {
        this.warningService = warningService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Warning> getWarnings() {
        return new ArrayList<>(warningService.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Warning getWarning(@PathVariable("id") Long id) {
        return warningService.getWarning(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Warning addWarning(@RequestBody Warning warning) {
        return warningService.addWarning(warning);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Warning updateWarning(@PathVariable("id") Long id, @RequestBody Warning warning) {
        return warningService.updateWarning(id, warning);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus deleteWarning(@PathVariable("id") Long id) {
        try {
            warningService.deleteWarning(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
