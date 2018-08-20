package org.soma.tripper.review.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Thumb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int thumbnum;

    @Column(name="bucket")
    private String bucket;
    @Builder
    Thumb(String bucket){
        this.bucket=bucket;
    }

}
