package com.example.accessingdatamysql.dto.request.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentFilterRequest extends GeneralFilterRequest {
    Integer userId;
    Integer documentId;
}
