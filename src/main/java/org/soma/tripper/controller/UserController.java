package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.dto.LoginUserDTO;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){ // 나중에 validator 사용해보기.
        if(userDTO.getEmail()==null || userDTO.getPassword()==null || userDTO.getDevice_token()==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userService.findUserByEmail(userDTO.getEmail())==null){
            userService.registerUser(userDTO);
            return new ResponseEntity<>(userDTO.toEntity(),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    @ApiOperation(value="Login user",notes = "로그인")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
    })
    public ResponseEntity<User> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        UserDTO userDTO= loginUserDTO.toDTO(loginUserDTO);
        if(userDTO.getEmail()==null||userDTO.getPassword()==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User user = userService.login(userDTO.getEmail(),userDTO.getPassword());
        if(user==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


}
