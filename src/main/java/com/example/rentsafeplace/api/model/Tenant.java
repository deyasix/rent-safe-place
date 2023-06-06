package com.example.rentsafeplace.api.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tenants")
@NoArgsConstructor
@Getter
@Setter
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "photo")
    private String photo;

    public Tenant(String email, String password, String name, String phone, String photo) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

}
