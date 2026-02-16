package com.elm.expensetracker.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private String roles;

    public Set<String> getRolesSet() {
        Set<String> roleSet = new HashSet<>();
        if (roles != null && !roles.isEmpty()) {
            String[] roleArray = roles.split(",");
            for (String role : roleArray) roleSet.add(role.trim());
        }
        return roleSet;
    }

}
