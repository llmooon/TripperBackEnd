package org.soma.tripper.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchDataException extends RuntimeException{
    public NoSuchDataException(){
        super();
    }
    public NoSuchDataException(String message){
        super(message);
    }
}
