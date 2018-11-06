package org.soma.tripper.user.service;

import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.dto.UserDTO;

import java.util.Optional;


public interface UserService {

    Optional<User> registerUser(User user);//user name 리턴.
    Optional<User> login(String id, String password);
    Optional<User> findUserByEmail(String user_email);
    Optional<User> findUserByUsernum(int usernum);
    Optional<User> validateUser(String str);
}
