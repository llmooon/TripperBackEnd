package org.soma.tripper.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String sender;
    private String recipient;
    private  String subject;
    private String content;
}
