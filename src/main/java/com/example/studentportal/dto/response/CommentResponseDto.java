package com.example.studentportal.dto.response;

import com.example.studentportal.model.Comment;
import com.example.studentportal.model.Document;
import com.example.studentportal.model.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class CommentResponseDto {
    private Integer id;

    private String text;

    private User user;

    private Date dateComment;

    private Document document;


    public static CommentResponseDto fromComment(Comment comment){
        CommentResponseDto responseDto = CommentResponseDto.builder()
                .id(comment.getId())
                .user(comment.getUser())
                .document(comment.getDocument())
                .dateComment(comment.getDateComment())
                .text(comment.getText())
                .build();
        return responseDto;
    }
}
