package org.soma.tripper.place.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlacePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photonum;

    @Lob
    @Column(columnDefinition = "text")
    private String bucket;
    @Builder
    PlacePhoto(String bucket){
        this.bucket=bucket;
    }

}
