package com.example.accessingdatamysql.dto.request;

import com.example.accessingdatamysql.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@ApiModel(description = "API model for update password")
public class UpdatePasswordRequest {

    @NotBlank(message = "require email address")
    private String email;
    private String password;
    private String newPassword;
}
