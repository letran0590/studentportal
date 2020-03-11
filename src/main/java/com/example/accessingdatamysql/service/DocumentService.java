package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.dto.request.CreateDocumentRequest;
import com.example.accessingdatamysql.dto.request.filter.DocumentFilterRequest;
import com.example.accessingdatamysql.dto.response.UploadDocumentResponseDto;
import com.example.accessingdatamysql.model.Document;
import org.springframework.core.io.Resource;

import java.util.List;

public interface DocumentService {
    Document storeFile(CreateDocumentRequest request);

    Resource loadFileAsResource(String fileName);

    List<UploadDocumentResponseDto> findAllPaging(DocumentFilterRequest request);
}
