package com.example.studentportal.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "All details about the Update comment request. ")
public class UpdateCommentRequest {
    @NonNull
    private String text;
}
