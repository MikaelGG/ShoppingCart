package com.users.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "user_type")
@Data
@NoArgsConstructor
public class UserTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;

    @OneToMany(mappedBy = "userType", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<UsersModel> users;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public UserTypeModel(Long id) {
        this.id = id;
    }

}
