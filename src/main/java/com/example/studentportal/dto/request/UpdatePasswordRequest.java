package com.example.studentportal.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@ApiModel(description = "API model for update password")
public class UpdatePasswordRequest {

    @NotBlank(message = "require email address")
    private String email;
    private String password;
    private String newPassword;
}
