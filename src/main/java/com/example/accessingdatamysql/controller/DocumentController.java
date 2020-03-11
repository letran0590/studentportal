package com.example.accessingdatamysql.controller;

import com.example.accessingdatamysql.common.ApiConstant;
import com.example.accessingdatamysql.dto.request.CreateDocumentRequest;
import com.example.accessingdatamysql.dto.request.filter.DocumentFilterRequest;
import com.example.accessingdatamysql.dto.response.ResponseDto;
import com.example.accessingdatamysql.dto.response.UploadDocumentResponseDto;
import com.example.accessingdatamysql.model.Document;
import com.example.accessingdatamysql.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiConstant.DOCUMENT) // This means URL's start with /demo (after Application path)
public class DocumentController {
	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

	private final DocumentService documentService;

	@Autowired
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@PostMapping("/upload")
	public ResponseDto<Document> uploadFile(CreateDocumentRequest request) {
		Document document = documentService.storeFile(request);
		return new ResponseDto<>(document);
	}

//	@PostMapping("/uploadMultipleFiles")
//	public List<UploadDocumentResponseDto> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//		return Arrays.asList(files)
//				.stream()
//				.map(file -> uploadFile(file))
//				.collect(Collectors.toList());
//	}

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = documentService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if(contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping("/filter")
	public List<UploadDocumentResponseDto> filter(DocumentFilterRequest request){
		return documentService.findAllPaging(request);
	}
}
