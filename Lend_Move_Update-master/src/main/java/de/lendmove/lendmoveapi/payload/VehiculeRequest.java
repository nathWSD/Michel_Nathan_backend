package de.lendmove.lendmoveapi.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class VehiculeRequest
{

    private Long id;

    @NotBlank
//    @Size(min = 3, max = 60)
    private String name;


    private String image;

    private String description;

    private Set<String> status = new HashSet<>();

    private String actualStation;

    private String createdBy;

    private String createdAt;

    private String city;

    private String category;

    private boolean available = true;

    private  String linkUrl;
    public VehiculeRequest()
    {

    }

    public VehiculeRequest(Long id, String name, String image, String description, Set<String> status,
                           String actualStation, String createdBy, String createdAt, String city, String category,
                           boolean available, String linkUrl) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.status = status;
        this.actualStation = actualStation;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.city = city;
        this.category = category;
        this.available  = available;
        this.linkUrl = linkUrl;
    }


    public VehiculeRequest(String name, String image, String description, Set<String> status,
                           String actualStation, String createdBy, String createdAt, String city, String category,
                           boolean available, String linkUrl) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.status = status;
        this.actualStation = actualStation;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.city = city;
        this.category = category;
        this.available  = available;
        this.linkUrl = linkUrl;

    }


}
