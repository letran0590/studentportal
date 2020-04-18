package com.example.studentportal.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ApiModel(description = "API model for update user info")
public class UpdateInfoRequest {

    private String firstName;

    private String lastName;

    private Date dob;

    private String address;

    @NotBlank(message = "Require email address")
    private String email;

    private List<MultipartFile> files;

}
