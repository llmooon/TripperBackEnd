package org.soma.tripper.place.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Array;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int search_num;

    private String country;
    private String city;
    private Array recommended1;
    private Array recommended2;
    private Array recommended3;
    private Array recommended4;
}
