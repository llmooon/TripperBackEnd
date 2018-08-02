package org.soma.tripper.practice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Phone {
    @Id
    @Column(name="seq")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int seq;

    @Column(name="member_id")
    private int memberId;

    @Column(name="no")
    private String no;

    @Override
    public String toString(){
        String result = "[phone_"+seq+"]"+no;
        return result;
    }
}
