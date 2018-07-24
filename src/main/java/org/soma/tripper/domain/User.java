package org.soma.tripper.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@ToString
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int user_num;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name="password")
    private String password;

    private String name;
    private String sex;

    @Builder
    public User(String email, String password){
        this.email = email;
        this.password=password;
    }

    public User(){ }

}
