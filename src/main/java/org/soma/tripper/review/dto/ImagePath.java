package org.soma.tripper.review.dto;

import lombok.*;

@Setter
@Getter
public class ImagePath {
    String fileName;
    String dateName;

    public ImagePath(){}

    @Builder
    public ImagePath(String fileName, String dateName){
        this.fileName=fileName;
        this.dateName=dateName;
    }
}
