package org.soma.tripper.place.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PlaceThumb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int thumbnum;

    @Column(name="bucket")
    private String bucket;
    @Builder
    PlaceThumb(String bucket){
        this.bucket=bucket;
    }

}
