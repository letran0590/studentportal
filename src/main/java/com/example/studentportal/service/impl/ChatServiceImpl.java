package com.example.studentportal.service.impl;

import com.example.studentportal.dto.request.filter.ChatFilterRequest;
import com.example.studentportal.dto.request.CreateChatRequest;
import com.example.studentportal.dto.response.ChatResponseDto;
import com.example.studentportal.dto.response.ResponseDto;
import com.example.studentportal.enumeration.Role;
import com.example.studentportal.exception.ResourceNotFoundException;
import com.example.studentportal.model.Chat;
import com.example.studentportal.model.User;
import com.example.studentportal.repository.ChatRepository;
import com.example.studentportal.repository.UserRepository;
import com.example.studentportal.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public ChatServiceImpl(final UserRepository userRepository,
                           final ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public List<Chat> findAll() {
        return null;
    }

    @Override
    public ResponseDto<ChatResponseDto> findById(Integer id) {
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ChatId " + id + " not found"));
        ChatResponseDto responseDto = ChatResponseDto.fromChat(chat);
        return new ResponseDto<>(responseDto);
    }

    @Override
    public Chat save(CreateChatRequest request) {
        // Check student id
        User student = userRepository.findAllByIdAndRole_Id(request.getStudentId(), Role.STUDENT.getValue());
        if (student == null) {
            throw new ResourceNotFoundException("StudentId " + request.getStudentId() + " not found");
        }
        // Check tutor id
        User tutor = userRepository.findAllByIdAndRole_Id(request.getTutorId(), Role.TUTOR.getValue());
        if (tutor == null) {
            throw new ResourceNotFoundException("TutorId " + request.getTutorId() + " not found");
        }

        Chat chat = new Chat();
        chat.setStudent(student);
        chat.setTutor(tutor);
        chat.setText(request.getText());
        chat.setDateChat(new Date());
        return chatRepository.save(chat);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<ChatResponseDto> findAllPaging(ChatFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getStart(), request.getLimit());
        Page<Chat> chats;
        if (null != request.getTutorId()) {
            chats = chatRepository.findAllByTutor_Id(request.getTutorId(), pageable);
        } else if (null != request.getStudentId()) {
            chats = chatRepository.findAllByStudent_Id(request.getStudentId(), pageable);
        } else {
            chats = chatRepository.findAll(pageable);
        }
        long total = chats.getTotalElements();
        List<ChatResponseDto> responseDtos;
        if (total > 0) {
            responseDtos = chats.stream().map(chat -> ChatResponseDto.fromChat(chat)).collect(Collectors.toList());
            return responseDtos;

        } else {
            return new ArrayList<>();
        }
    }
}
