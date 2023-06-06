package com.example.rentsafeplace.service;

import com.example.rentsafeplace.api.model.SubscriptionType;
import com.example.rentsafeplace.api.repository.SubscriptionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SubscriptionTypeService {

    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;

    public ArrayList<SubscriptionType> getAll() {
        return new ArrayList<>(subscriptionTypeRepository.findAll());
    }

    public SubscriptionType getSubscriptionType(Long id) {
        return subscriptionTypeRepository.findById(id).orElse(null);
    }

    public SubscriptionType addSubscriptionType(SubscriptionType subscriptionType) {
        return subscriptionTypeRepository.save(subscriptionType);
    }

    public SubscriptionType updateSubscriptionType(Long id, SubscriptionType subscriptionType) {
        Optional<SubscriptionType> data = subscriptionTypeRepository.findById(id);
        if (data.isPresent()) {
            SubscriptionType _subscriptionType = data.get();
            if (subscriptionType.getName() != null) {
                _subscriptionType.setName(subscriptionType.getName());
            }
            if (subscriptionType.getPrice() != null) {
                _subscriptionType.setPrice(subscriptionType.getPrice());
            }
            if (subscriptionType.getTerm() != null) {
                _subscriptionType.setTerm(subscriptionType.getTerm());
            }
            return subscriptionTypeRepository.save(_subscriptionType);
        } else {
            return null;
        }
    }

    public void deleteSubscriptionType(Long id) {
        subscriptionTypeRepository.deleteById(id);
    }
}
