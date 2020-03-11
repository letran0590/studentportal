package com.example.accessingdatamysql.dto.request;

import com.example.accessingdatamysql.model.Document;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ApiModel(description = "All details about the Create chat request. ")
public class CreateDocumentRequest {
    @NonNull
    private Integer studentId;

    @NonNull
    private Integer tutorId;

    @NonNull
    private MultipartFile file;
}
