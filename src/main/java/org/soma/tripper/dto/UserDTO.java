package org.soma.tripper.dto;

import lombok.*;
import org.soma.tripper.domain.User;
import org.springframework.context.annotation.Bean;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class UserDTO {

    private String email;
    private String password;

    private String name;
    private int sex;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .sex(sex)
                .build();
    }
}
