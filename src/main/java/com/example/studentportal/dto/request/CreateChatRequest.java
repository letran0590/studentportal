package com.example.studentportal.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "All details about the Create chat request. ")
public class CreateChatRequest {
    @NonNull
    private Integer studentId;

    @NonNull
    private Integer tutorId;

    @NonNull
    private Integer senderId;

    @NonNull
    private String text;

}
