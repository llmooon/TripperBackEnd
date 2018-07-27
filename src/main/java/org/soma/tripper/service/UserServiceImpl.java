package org.soma.tripper.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.domain.User;
import org.soma.tripper.dto.UserDTO;
import org.soma.tripper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional//위치가 여기가 맞는가
    public String registerUser(UserDTO userDTO) {
        try{
          User user = userRepository.save(userDTO.toEntity());
        }
        catch (Exception e){
            throw new DuplicateKeyException("duplicated");
        }
       return userDTO.getEmail();
    }
}
