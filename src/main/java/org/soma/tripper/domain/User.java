package org.soma.tripper.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int user_num;
    private String user_email;
    private String password;

    @Builder
    public User(String user_email, String password){
        this.user_email=user_email;
        this.password=password;
    }

    public User(){

    }

}
