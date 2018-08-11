package org.soma.tripper.review.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
public class Thumb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int thumbnum;

    @Column(name="bucket")
    private String bucket;

    public Thumb(){

    }
    @Builder
    Thumb(String bucket){
        this.bucket=bucket;
    }

}
