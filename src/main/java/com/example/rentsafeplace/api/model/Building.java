package com.example.rentsafeplace.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "buildings")
@NoArgsConstructor
@Getter
@Setter
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "realtor_id", referencedColumnName = "realtor_id")
    private Realtor realtor;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
    private Tenant tenant;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "square", nullable = false)
    private Integer square;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "is_pet_allowed", nullable = false)
    private Boolean isPetAllowed;

    @Column(name = "photo")
    private String photo;

    @Column(name = "description")
    private String description;

    @Column(name = "is_leased", nullable = false)
    private Boolean isLeased;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address")
    private String address;


}
