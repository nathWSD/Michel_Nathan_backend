package de.lendmove.lendmoveapi.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.lendmove.lendmoveapi.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private String birthday;

    private String phonenumber;

    private  String postalCode;

    private String streetNameAndNumber;

    private String country;

    private String city;

    private String firstname;

    private String surname;

    private String createdAt;
    private String status;

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities, String phonenumber, String birthday,
                           String postalCode, String streetNameAndNumber, String country, String city,
                           String firstname,  String surname, String createdAt, String status )
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.birthday = birthday;
        this.phonenumber = phonenumber;
        this.postalCode = postalCode;
        this.streetNameAndNumber =   streetNameAndNumber;
        this.country = country;
        this.city = city;
        this.firstname = firstname;
        this.surname = surname;
        this.createdAt = createdAt;
        this.status = status;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getBirthday(),
                user.getPhonenumber(),
                user.getPostalCode(),
                user.getStreetNameAndNumber(),
                user.getCountry(),
                user.getCity(),
                user.getFirstname(),
                user.getSurname(),
                user.getCreatedAt(),
                user.getStatus());




    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}