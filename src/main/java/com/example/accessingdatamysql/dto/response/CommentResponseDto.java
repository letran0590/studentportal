package com.example.accessingdatamysql.dto.response;

import com.example.accessingdatamysql.model.Comment;
import com.example.accessingdatamysql.model.Document;
import com.example.accessingdatamysql.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {
    private Integer id;

    private String text;

    private User user;

    private Document document;


    public static CommentResponseDto fromComment(Comment comment){
        CommentResponseDto responseDto = CommentResponseDto.builder()
                .id(comment.getId())
                .user(comment.getUser())
                .document(comment.getDocument())
                .build();
        return responseDto;
    }
}
