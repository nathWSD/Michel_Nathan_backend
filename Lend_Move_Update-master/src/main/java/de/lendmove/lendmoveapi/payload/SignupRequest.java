package de.lendmove.lendmoveapi.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    private Long id;
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @Size(max = 100)
    private String city;

    @Size(max = 25)

    private String postalCode;
    @Size(max = 40)
    @Column(nullable = true)
    private String country;
    @Size(max = 50)
    private String streetNameAndNumber;
    private String phonenumber;

    private String status;
    @Size(max = 25)
    private String firstname;


    @Size(max = 25)
    private String surname;

    private String tarif;

    private String birthday;

    private String   checkpassword;


}