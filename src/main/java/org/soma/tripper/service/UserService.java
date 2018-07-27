package org.soma.tripper.service;

import org.soma.tripper.domain.User;
import org.soma.tripper.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {

    String registerUser(UserDTO userDTO);//user name 리턴.
}
