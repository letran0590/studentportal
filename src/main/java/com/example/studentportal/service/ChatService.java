package com.example.studentportal.service;

import com.example.studentportal.dto.request.filter.ChatFilterRequest;
import com.example.studentportal.dto.request.CreateChatRequest;
import com.example.studentportal.dto.response.ChatResponseDto;
import com.example.studentportal.dto.response.ResponseDto;
import com.example.studentportal.model.Chat;

import java.util.List;

public interface ChatService {
    List<Chat> findAll();

    ResponseDto<ChatResponseDto> findById(Integer id);

    Chat save(CreateChatRequest request);

    void deleteById(Integer id);

    List<ChatResponseDto> findAllPaging(ChatFilterRequest request);

}
