package org.soma.tripper.user.service;

import org.soma.tripper.user.dto.Email;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {
    void sendMail(Email email) throws MessagingException;
}
