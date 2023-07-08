package de.lendmove.lendmoveapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "statusVehicule")
@Getter
@Setter
public class StatusVehicule {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumStatusVehicule name;
    public  StatusVehicule(){
    }

    public StatusVehicule(EnumStatusVehicule name){this.name=name;}
}
