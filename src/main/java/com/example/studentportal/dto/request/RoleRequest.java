package com.example.studentportal.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel(description = "All details about the Create user request. ")
public class RoleRequest {
    @NotBlank(message = "is required, but it was not defined.")
    private String name;
}
