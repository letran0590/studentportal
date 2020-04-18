package com.example.studentportal.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel(description = "All details about the Create user request. ")
public class UpdateUserRequest {
    private List<Integer> studentIds;
    private Integer tutorId;
}
