package com.example.studentportal.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDocumentDir;
    //file.upload-document-dir
    private String uploadAvatarDir;
    private String defaultPassword;
}
