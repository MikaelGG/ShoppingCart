package com.users.dto;

import com.users.models.UserTypeModel;
import lombok.Data;

@Data
public class UsersDTO {

    private String fullName;
    private String phoneNumber;
    private String email;
    private String currentPassword;
    private String newPassword;
    private String repeatNewPassword;
    private UserTypeModel userType;

}
