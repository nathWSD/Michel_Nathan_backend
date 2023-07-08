package de.lendmove.lendmoveapi.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String tarif;
    private List<String> roles;

    private String phonenumber;

    private String birthday;
    private  String postalCode;
    private  String streetNameAndNumber;

    private String country;

    private String city;

    private String firstname;

    private String surname;

    private String createdAt;
    String status;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles, String tarif,
                       String phonenumber, String birthday, String postalCode , String streetNameAndNumber,
                       String country, String city, String firstname, String surname, String createdAt,String status  ) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.tarif = tarif;
        this.phonenumber = phonenumber;
        this.birthday = birthday;
        this.postalCode = postalCode;
        this.streetNameAndNumber = streetNameAndNumber;
        this.country = country;
        this.city = city;
        this.firstname = firstname;
        this.surname = surname;
        this.createdAt = createdAt;
        this.status = status;
    }


}
