package org.soma.tripper.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.soma.tripper.user.dto.UserDTO;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@ToString
@Entity
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    private String url="https://s3.ap-northeast-2.amazonaws.com/tripper-bucket/default.png";


    @Builder User( String name, String email, String password, int sex, String device_token){
        this.sex=sex;
        this.name=name;
        this.password=password;
        this.email=email;
        this.device_token=device_token;
    }


    public UserDTO toDTO(){
        return UserDTO.builder()
                .email(email)
                .name(name)
                .password(null)
                .device_token(device_token)
                .sex(sex)
                .build();
    }
}
