package com.example.studentportal.dto.response;

import com.example.studentportal.model.Document;
import com.example.studentportal.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadDocumentResponseDto {
    private Integer id;

    private String name;

    private Date uploadedDate;

    private Date lastModified;

    private User student;

    private User tutor;

    private String fileDownloadUri;

    private String fileType;

    private long size;

    public static UploadDocumentResponseDto fromDocument(Document document) {
        UploadDocumentResponseDto responseDto = UploadDocumentResponseDto.builder()
                .id(document.getId())
                .name(document.getName())
                .uploadedDate(document.getUploadedDate())
                .lastModified(document.getLastModified())
                .student(document.getStudent())
                .tutor(document.getTutor())
                .fileDownloadUri(document.getFileDownloadUri())
                .fileType(document.getFileType())
                .size(document.getSize())
                .build();
        return responseDto;
    }
}
