package org.soma.tripper.user.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.dto.UserDTO;
import org.soma.tripper.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new NullPointerException("npe"); //널 값, 중복 값 예외 처리 하기
        }
        return userDTO.toString();
    }

    @Override
    public Optional<User> login(String id, String password) {
        return Optional.ofNullable(userRepository.findUserByEmailAndPassword(id,password));
    }

    @Override
    public User findUserByEmail(String user_email) {
        return userRepository.findByEmail(user_email);
    }

    @Override
    public Optional<User> findUserByUsernum(int usernum) {
        return userRepository.findById(usernum);
    }
}
