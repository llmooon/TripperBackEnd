package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.soma.tripper.domain.User;
import org.soma.tripper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(value="User Controller",description = "유저 API")

public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/create/{user_id}/{password}")
    @ApiOperation(value="User api",notes = "회원가입")
    public ResponseEntity<User> createUser(@PathVariable("user_id") String user_id, @PathVariable("password") String pw){
        User user = userRepository.save(User.builder().email(user_id).password(pw).build());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/login/{user_id}/{password}")
    @ApiOperation(value="User api",notes = "로그인")
    public ResponseEntity<User> loginUser(@PathVariable("user_id") String user_id, @PathVariable("password") String pw){
        User user = userRepository.findUserByEmailAndPassword(user_id,pw);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
