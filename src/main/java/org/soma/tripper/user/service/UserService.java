package org.soma.tripper.user.service;

import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.dto.UserDTO;

import java.util.Optional;


public interface UserService {

    String registerUser(UserDTO userDTO);//user name 리턴.
    Optional<User> login(String id, String password);
    User isInEmail(String user_email);
}
