package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.dto.request.filter.ChatFilterRequest;
import com.example.accessingdatamysql.dto.request.CreateChatRequest;
import com.example.accessingdatamysql.dto.response.ChatResponseDto;
import com.example.accessingdatamysql.dto.response.ResponseDto;
import com.example.accessingdatamysql.model.Chat;

import java.util.List;

public interface ChatService {
    List<Chat> findAll();

    ResponseDto<ChatResponseDto> findById(Integer id);

    Chat save(CreateChatRequest request);

    void deleteById(Integer id);

    List<ChatResponseDto> findAllPaging(ChatFilterRequest request);

}
