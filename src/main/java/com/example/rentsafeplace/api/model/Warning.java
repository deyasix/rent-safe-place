package com.example.rentsafeplace.api.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "warnings")
@NoArgsConstructor
@Getter
@Setter
public class Warning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warning_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "building_id")
    private Building building;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "type", nullable = false)
    private String type;

}
