package com.example.accessingdatamysql.dto.request.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFilterRequest extends GeneralFilterRequest{
    Integer roleId;
    boolean tutorFlag;
}
