package com.example.accessingdatamysql.dto.response;

import com.example.accessingdatamysql.model.Role;
import com.example.accessingdatamysql.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Role role;

    private User tutor;

    public static UserResponseDto fromUser(User user){
        UserResponseDto responseDto = UserResponseDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .email(user.getEmail())
                .tutor(user.getTutor())
                .build();
        return responseDto;
    }
}
