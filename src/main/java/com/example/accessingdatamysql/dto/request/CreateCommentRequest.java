package com.example.accessingdatamysql.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ApiModel(description = "All details about the Create comment request. ")
public class CreateCommentRequest {
    @NonNull
    private Integer userId;

    @NonNull
    private Integer documentId;

    @NonNull
    private String text;
}
