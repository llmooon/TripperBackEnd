package org.soma.tripper.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@ToString
@Entity
//@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int user_num;

    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @Column(name="password", nullable = false)
    private String password;

    @Column(name="name",nullable = false)
    private String name;
    private int sex;


    @Builder User(int user_num, String name, String email, String password, int sex){
        this.sex=sex;
        this.user_num=user_num;
        this.name=name;
        this.password=password;
        this.email=email;
    }

    public User(){}
}
