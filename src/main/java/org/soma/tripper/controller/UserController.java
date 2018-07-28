package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.domain.User;
import org.soma.tripper.dto.UserDTO;
import org.soma.tripper.repository.UserRepository;
import org.soma.tripper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@Api(value="User Controller",description = "유저 API")

public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @PostMapping("/create")
    @ApiOperation(value="User api",notes = "회원가입")
    @ApiResponses(
            value={@ApiResponse(code=201,message="Create Success"),
                    @ApiResponse(code=400,message="Bad Request(duplicated key)")
            })
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        try{
            userService.registerUser(userDTO);
        }
        catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login/{user_email}/{password}")
    @ApiOperation(value="User api",notes = "로그인")
    @ApiResponses(
            value={@ApiResponse(code=200,message="Successfully retrieved"),
            })
    public ResponseEntity<User> loginUser(@PathVariable String user_email, @PathVariable String password){
        User user = userService.login(user_email,password).orElseThrow(()->new NoSuchElementException("회원 정보가 유호하지 않습니다."));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
