package com.example.rentsafeplace.api.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id", nullable = false, unique = true)
    private Long id;

    @OneToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", nullable = false, unique = true)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "subscription_type_id", referencedColumnName = "subscription_type_id")
    private SubscriptionType subscriptionType;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;

}
