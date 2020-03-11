package com.example.accessingdatamysql.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel(description = "All details about the Create user request. ")
public class UpdateUserRequest {
    private List<Integer> studentIds;
    private Integer tutorId;
}
