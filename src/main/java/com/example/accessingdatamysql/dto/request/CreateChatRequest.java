package com.example.accessingdatamysql.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel(description = "All details about the Create chat request. ")
public class CreateChatRequest {
    @NonNull
    private Integer studentId;

    @NonNull
    private Integer tutorId;

    @NonNull
    private String text;

}
