package com.example.rentsafeplace.api.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscription_types")
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_type_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "term")
    private String term;

    public SubscriptionType(String name, Integer price, String term) {
        this.name = name;
        this.price = price;
        this.term = term;
    }

}
