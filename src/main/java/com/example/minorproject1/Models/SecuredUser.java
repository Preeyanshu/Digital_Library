package com.example.minorproject1.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
@Entity
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecuredUser implements UserDetails {


    private static final String AUTHORITTIES_DELIMITER = "::";
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true,nullable = false)     //username must be unique
    private String username;
    private String password;
    private String authorities;     // login_faculty::access_library

    @Getter
    @OneToOne(mappedBy = "securedUser")
    @JsonIgnoreProperties(value = {"securedUser"})
    private Student student;
    @Getter
    @OneToOne(mappedBy = "securedUser")
    @JsonIgnoreProperties(value = {"securedUser"})
    private Admin admin;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] authoritiesList = this.authorities.split(AUTHORITTIES_DELIMITER);
        return Arrays.stream(authoritiesList)
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
}
