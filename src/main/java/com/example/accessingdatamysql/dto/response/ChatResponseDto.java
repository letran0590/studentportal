package com.example.accessingdatamysql.dto.response;

import com.example.accessingdatamysql.model.Chat;
import com.example.accessingdatamysql.model.Role;
import com.example.accessingdatamysql.model.User;
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


    public static ChatResponseDto fromChat(Chat chat){
        ChatResponseDto responseDto = ChatResponseDto.builder()
                .id(chat.getId())
                .student(chat.getStudent())
                .tutor(chat.getTutor())
                .text(chat.getText())
                .chatDate(chat.getDateChat())
                .build();
        return responseDto;
    }
}