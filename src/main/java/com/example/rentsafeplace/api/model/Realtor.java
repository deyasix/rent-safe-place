package com.example.rentsafeplace.api.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "realtors")
@NoArgsConstructor
@Getter
@Setter
public class Realtor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "realtor_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

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

    public Realtor(Company company, String email, String password, String name, String phone, String photo) {
        this.company = company;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }
}
