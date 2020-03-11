package com.example.accessingdatamysql.dto.request.filter;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class GeneralFilterRequest {
    @Min(0)
    protected int start = 0;
    @Min(1)
    protected int limit;
}
