package com.example.studentportal.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(description = "All details about the Create blog request. ")
public class CreateBlogRequest {

    @NotNull
    private int userId;

    @NotNull
    private String name;

    @NotNull
    private String body;
}
