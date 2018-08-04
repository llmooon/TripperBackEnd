package org.soma.tripper.user.dto;

import lombok.Getter;

@Getter
public class LoginUserDTO{
    private String email;
    private String password;
    public UserDTO toDTO(LoginUserDTO loginUserDTO){
        return UserDTO.builder()
                .email(email)
                .password(password)
                .build();
    }
}