package org.soma.tripper.practice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @Column(name="seq")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int seq;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Collection<Phone> phone;

    public void addPhone(Phone p){
        if(phone==null){
            phone=new ArrayList<>();
        }
        phone.add(p);
    }

    @Override
    public String toString() {
        String result = "["+seq+"]"+name;
        for(Phone p : phone){
            result+="\n"+p.toString();
        }
        return result;
    }
}
