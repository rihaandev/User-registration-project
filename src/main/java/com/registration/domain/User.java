package com.registration.domain;

import com.registration.domain.constants.Gender;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * Entity class for User
 * @author mohamed.rihan
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
@ToString(callSuper = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "role")
    private String role;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "country")
    private String country;
}