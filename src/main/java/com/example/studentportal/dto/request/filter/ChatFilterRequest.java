package com.example.studentportal.dto.request.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatFilterRequest extends GeneralFilterRequest {
    Integer studentId;
    Integer tutorId;
}
