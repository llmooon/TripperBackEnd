package org.soma.tripper.review.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ReviewDTO {
    private String user;
    private int seqnum;
    private List<DetailDTO> reviews;

    //toEntity cannot make.

}
