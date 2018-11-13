package org.soma.tripper.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int user_num;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;
    private int sex;
    private String device_token;
    private String validateUrl;

    public void setValidateUrl(String validateUrl) {
        this.validateUrl = validateUrl;
    }

    @Builder
    TempUser(String name, String email, String password, int sex, String device_token){
        this.sex=sex;
        this.name=name;
        this.password=password;
        this.email=email;
        this.device_token=device_token;
    }

    public User toUser(){
        return User.builder()
                .device_token(this.device_token)
                .email(this.email)
                .name(name)
                .password(password)
                .sex(sex)
                .build();
    }
}
