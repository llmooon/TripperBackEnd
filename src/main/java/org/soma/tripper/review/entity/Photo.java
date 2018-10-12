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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photonum;

    private String bucket;

    @Builder
    Photo(String bucket){
        this.bucket=bucket;
    }
}
