package com.users.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class usersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 105)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 55)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 105)
    private String email;

    @Column(name = "password", nullable = false, length = 85)
    private String password;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user_type", nullable = false)
    private userTypeModel userType;

}
