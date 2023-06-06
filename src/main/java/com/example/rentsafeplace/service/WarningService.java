package com.example.rentsafeplace.service;

import com.example.rentsafeplace.api.model.Warning;
import com.example.rentsafeplace.api.repository.WarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class WarningService {
    @Autowired
    private WarningRepository warningRepository;

    public Warning getWarning(Long id) {
        return warningRepository.findById(id).orElse(null);
    }
    public ArrayList<Warning> getAll() {
        return new ArrayList<>(warningRepository.findAll());
    }
    public Warning addWarning(Warning warning) {
        return warningRepository.save(warning);
    }

    public Warning updateWarning(Long id, Warning warning) {
        Optional<Warning> warningData = warningRepository.findById(id);
        if (warningData.isPresent()) {
            Warning _warning = warningData.get();
            if (warning.getBuilding() != null) {
                _warning.setBuilding(warning.getBuilding());
            }
            if (warning.getMessage() != null) {
                _warning.setMessage(warning.getMessage());
            }
            if (warning.getType() != null) {
                _warning.setType(warning.getType());
            }
            if (warning.getTime() != null) {
                _warning.setTime(warning.getTime());
            }
            return warningRepository.save(_warning);
        } else {
            return null;
        }
    }

    public void deleteWarning(Long id) {
        warningRepository.deleteById(id);
    }
}
