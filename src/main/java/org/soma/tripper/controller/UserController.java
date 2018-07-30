package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.dto.UserDTO;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@Api(value="User Controller",description = "유저 API")

public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @PostMapping("/create")
    @ApiOperation(value="Register user",notes = "회원가입")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){

        if(userDTO.getEmail()==null || userDTO.getPassword()==null || userDTO.getDevice_token()==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(userService.isInEmail(userDTO.getEmail())==null){
            userService.registerUser(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login/{user_email}/{password}")
    @ApiOperation(value="Login user",notes = "로그인")
    public ResponseEntity<User> loginUser(@PathVariable String user_email, @PathVariable String password){
        if(user_email==null||password==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User user = userService.login(user_email,password).orElseThrow(()->new NoSuchElementException("회원 정보가 유호하지 않습니다."));
        if(user==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
