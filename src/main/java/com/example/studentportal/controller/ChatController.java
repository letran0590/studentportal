package com.example.studentportal.controller;

import com.example.studentportal.common.ApiConstant;
import com.example.studentportal.dto.request.filter.ChatFilterRequest;
import com.example.studentportal.dto.request.CreateChatRequest;
import com.example.studentportal.dto.response.ChatResponseDto;
import com.example.studentportal.dto.response.ResponseDto;
import com.example.studentportal.model.Chat;
import com.example.studentportal.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j // This means that this class is a Controller
@RequestMapping(ApiConstant.CHAT) // This means URL's start with /demo (after Application path)
public class ChatController {
	private final ChatService chatService;

	@Autowired
	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@PostMapping("/")
	public ResponseEntity<Chat> create(@RequestBody @Valid CreateChatRequest request){
		return ResponseEntity.ok(chatService.save(request));
	}

	@GetMapping("/{chatId}")
	public ResponseDto<ChatResponseDto> getChatById(@PathVariable Integer chatId){
		return chatService.findById(chatId);
	}

	@GetMapping("/filter")
	public List<ChatResponseDto> filter(ChatFilterRequest request){
		return chatService.findAllPaging(request);
	}
}
