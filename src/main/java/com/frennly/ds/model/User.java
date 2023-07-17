package com.frennly.ds.model;

import com.frennly.ds.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_details")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    private String preferredTimings;

    private Integer score;

    private Double ratings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(profileImage, user.profileImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, profileImage);
    }
}
