package org.soma.tripper.place.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Array;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Entity
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int search_num;

    private String name;
    private String recommended1;
    private String recommended2;
    private String recommended3;
    private String recommended4;

    @Builder
    public Search(String name){
        this.name=name;
    }
}
