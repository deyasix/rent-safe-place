package com.example.rentsafeplace.api.repository;

import com.example.rentsafeplace.api.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
