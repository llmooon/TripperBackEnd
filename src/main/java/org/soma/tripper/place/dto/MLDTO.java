package org.soma.tripper.place.dto;

import lombok.*;
import org.soma.tripper.user.domain.User;

import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MLDTO {

    private int sex;
    private int age;

    private int days;
    private int shopping;
    private int food;
    private int tourist;
    private int culture;
    private int Entertainment;
    private boolean withChildren;
    private boolean withElderly;

    @Builder
    public MLDTO(PurposeDTO purposeDTO, Optional<User> user){
        this.sex = user.get().getSex();
        //this.age = user.getAge();     if add age, Add!
        this.days = purposeDTO.getDays();
        this.shopping = purposeDTO.getShopping();
        this.food = purposeDTO.getFood();
        this.tourist = purposeDTO.getTourist();
        this.culture = purposeDTO.getCulture();
        Entertainment = purposeDTO.getEntertainment();
        this.withChildren = purposeDTO.isWithChildren();
        this.withElderly = purposeDTO.isWithElderly();
    }
}
