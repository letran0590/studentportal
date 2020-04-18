package com.example.studentportal.dto.request.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFilterRequest extends GeneralFilterRequest{
    Integer roleId;
    boolean tutorFlag;
}
