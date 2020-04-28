package com.example.studentportal.dto.response;

import com.example.studentportal.model.Chat;
import com.example.studentportal.model.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ChatResponseDto {
    private Integer id;

    private User student;

    private User tutor;

    private String text;

    private Date chatDate;

    private User sender;


    public static ChatResponseDto fromChat(Chat chat){
        ChatResponseDto responseDto = ChatResponseDto.builder()
                .id(chat.getId())
                .student(chat.getStudent())
                .tutor(chat.getTutor())
                .text(chat.getText())
                .chatDate(chat.getDateChat())
                .sender(chat.getSender())
                .build();
        return responseDto;
    }
}
