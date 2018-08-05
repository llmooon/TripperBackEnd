package org.soma.tripper.user.dto;

import lombok.*;
import org.soma.tripper.user.domain.User;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UserDTO {


    private String email;
    private String password;
    private String name;
    private int sex;
    private String device_token;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .sex(sex)
                .device_token(device_token)
                .build();
    }

    @Builder
    public UserDTO(String email, String password, String name, int sex, String device_token) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.device_token = device_token;
    }
    @Builder
    public UserDTO(String email, String password){
        this.email=email;
        this.password=password;
    }
}
