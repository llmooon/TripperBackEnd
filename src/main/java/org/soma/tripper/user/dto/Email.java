package org.soma.tripper.user.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Email {
    private String sender;
    private String recipient;
    private  String subject;
    private String content;
    private Map<String,Object> model;

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
