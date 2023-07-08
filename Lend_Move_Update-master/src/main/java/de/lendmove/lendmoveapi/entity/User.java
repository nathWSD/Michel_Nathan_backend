package de.lendmove.lendmoveapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represent  a account on the plattform.
 */
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@DynamicUpdate
@Getter
@Setter
@ToString
public class User
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 25)
    @Column(nullable = true, unique = true)
    private String username;

    @Column(nullable = true)
    @NotBlank
    @Size(max = 25)
    private String firstname;

    @NotBlank
    @Size(max = 25)
    @Column(nullable = true)
    private String surname;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = true, unique = true)
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = true)
    private String password;


    @Size(max = 100)
    @Column
    private String city;


    @Size(max = 25)
    @Column(nullable = true)
    private String postalCode;


    @Size(max = 40)
    @Column(nullable = true)
    private String country;


    @Size(max = 50)
    @Column(nullable = true)
    private String streetNameAndNumber;

    @Column(nullable = true, unique = true)
    private String phonenumber;


    @Column(nullable = true)
    private String status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();




    @Column(nullable = true)
    private String createdAt;

    private String tarif;

    private String checkpassword;

    private String birthday;

    public User() {
        super();
        this.createdAt =  LocalDate.now().toString();
    }

    public User( String name, String surname,  String email, String password, String streetNameAndNumber,
                String phonenumber,
                String country, String city, String birthday, String postalCode, String status
    ) {
        super();
        this.firstname = name;
        this.surname = surname;
        this.email = email;
        this.password =  password;
        this.phonenumber = phonenumber;
        this.country = country;
        this.createdAt = LocalDate.now().toString();
        this.streetNameAndNumber = streetNameAndNumber;
        this.city = city;
        this.birthday = birthday;
        this.postalCode = postalCode;
        this.status = status;
    }



    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
