package com.stussy.stussy.clone20220929ng.dto;

import lombok.Data;

@Data
public class RegisterReqDto {
    private String lastName;
    private String firstName;
    private String email;
    private String password;
}
