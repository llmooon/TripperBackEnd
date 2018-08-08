package org.soma.tripper.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.soma.tripper.review.entity.Photo;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Getter
@Setter
public class PhotoDTO {

    private byte[] photo;
    private HttpHeaders httpHeaders;

    public PhotoDTO(){}

    @Builder
    public PhotoDTO(byte[] photo, HttpHeaders httpHeaders){
        this.photo=photo;
        this.httpHeaders=httpHeaders;
    }
}
