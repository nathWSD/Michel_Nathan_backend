package de.lendmove.lendmoveapi.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "stations")
@DynamicUpdate
@Getter
@Setter
public class Station
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String imageUrl;


    public Station()
    {

    }

    public Station(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
