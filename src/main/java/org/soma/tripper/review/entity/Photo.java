package org.soma.tripper.review.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int photonum;

    @Column(name="bucket")
    private String bucket;

    @Column(name="reviewnum")
    private int reviewnum;

    @Builder
    Photo(String bucket){
        this.bucket=bucket;
    }
}
