package de.lendmove.lendmoveapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role
{
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumRole name;
    public  Role(){
    }

    public  Role(EnumRole name)
    {
        this.name = name;
    }
}
