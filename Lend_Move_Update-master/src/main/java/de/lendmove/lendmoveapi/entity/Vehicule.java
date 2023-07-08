package de.lendmove.lendmoveapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicules")
@DynamicUpdate
@Getter
@Setter
public class Vehicule
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private String name;

    private String image;

    private String description;

    private boolean available = true;

    private String city;

    private String category;

    private String linkUrl;

    public boolean isAvailable() {
        return available;

    }

    public Vehicule setAvailable(boolean available) {
        this.available = available;
        return null;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "vehicule_status",
            joinColumns = @JoinColumn(name = "vehicule_id"),
            inverseJoinColumns = @JoinColumn(name = "statusVehicule_id"))
    private Set<StatusVehicule> status = new HashSet<>();

    private String actualStation;

    private String createdBy;

    private String createdAt;

    public Vehicule()
    {
        this.createdAt = new Date().toString();
    }

    public Vehicule(String name, String description,String actualStation, Set<StatusVehicule> status,
                    boolean available, String city, String category, String image, String linkUrl)
    {
        this.name= name;
        this.description = description;
        this.status = status;
        this.actualStation = actualStation;
        this.available = available;
        this.city = city;
        this.category = category;
        this.image = image;
        this.linkUrl = linkUrl;
    }
}
