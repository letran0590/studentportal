package com.example.accessingdatamysql.dto.request;

import com.example.accessingdatamysql.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@ApiModel(description = "All details about the Create user request. ")
public class CreateUserRequest {
    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private Date dob;

    @NonNull
    private String email;

    private String address;

    @NonNull
    private String role;

    @NonNull
    private String password;

    private Integer tutorId;

    @Builder.Default()
    private String gender = "male";
}
