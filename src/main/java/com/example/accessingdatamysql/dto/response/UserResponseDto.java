package com.example.accessingdatamysql.dto.response;

import com.example.accessingdatamysql.model.Role;
import com.example.accessingdatamysql.model.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class UserResponseDto {
    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Role role;

    private User tutor;

    private Date dob;

    public static UserResponseDto fromUser(User user){
        UserResponseDto responseDto = UserResponseDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .email(user.getEmail())
                .tutor(user.getTutor())
                .dob(user.getDob())
                .address(user.getAddress())
                .build();
        return responseDto;
    }
}
