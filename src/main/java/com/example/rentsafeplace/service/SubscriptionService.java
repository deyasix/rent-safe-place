package com.example.rentsafeplace.service;

import com.example.rentsafeplace.api.model.Subscription;
import com.example.rentsafeplace.api.model.SubscriptionType;
import com.example.rentsafeplace.api.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionTypeService subscriptionTypeService;

    public ArrayList<Subscription> getAll() {
        return new ArrayList<>(subscriptionRepository.findAll());
    }

    public Subscription getSubscription(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    public Subscription addSubscription(Subscription subscription) {
        SubscriptionType subscriptionType = subscriptionTypeService.getSubscriptionType(subscription.getSubscriptionType().getId());
        if (subscriptionType == null) return null;
        subscription.setSubscriptionType(subscriptionType);
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateSubscription(Long id, Subscription subscription) {
        Optional<Subscription> subscriptionData = subscriptionRepository.findById(id);
        if (subscriptionData.isPresent()) {
            Subscription _subscription = subscriptionData.get();
            if (subscription.getStartDate() != null) {
                _subscription.setStartDate(subscription.getStartDate());
            }
            if (subscription.getEndDate() != null) {
                _subscription.setEndDate(subscription.getEndDate());
            }
            if (subscription.getCompany() != null) {
                _subscription.setCompany(subscription.getCompany());
            }
            if (subscription.getSubscriptionType() != null) {
                SubscriptionType subscriptionType = subscriptionTypeService.getSubscriptionType(subscription.getSubscriptionType().getId());
                _subscription.setSubscriptionType(subscriptionType);
            }
            return subscriptionRepository.save(_subscription);
        } else {
            return null;
        }
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
