package org.soma.tripper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.common.exception.NoSuchDataException;
import org.soma.tripper.user.domain.TempUser;
import org.soma.tripper.user.domain.User;
import org.soma.tripper.user.dto.Email;
import org.soma.tripper.user.dto.LoginUserDTO;
import org.soma.tripper.user.dto.UserDTO;
import org.soma.tripper.user.service.EmailService;
import org.soma.tripper.user.service.TempUserService;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

@RestController
@RequestMapping("/user")
@Api(value="User Controller",description = "유저 API")

public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    TempUserService tempUserService;

    @PostMapping("/create")
    @ApiOperation(value="Register user",notes = "회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws Exception{ // 나중에 validator 사용해보기.
        if(userDTO.getEmail()==null || userDTO.getPassword()==null || userDTO.getDevice_token()==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(userService.findUserByEmail(userDTO.getEmail()).isPresent() || tempUserService.findUserByEmail(userDTO.getEmail()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        String url = getkey();
        Map<String,Object> model = new HashMap();
        model.put("name","tripper.com");
        model.put("url",url);
        try {
            emailService.sendMail(new Email("Tripper@tripper.com", userDTO.getEmail(), "tripper 가입을 축하 드립니다! ", "hello", model));

        }
        catch (Exception e){

        }
        TempUser user = userDTO.toTempUserEntity();
        user.setValidateUrl(url);
        tempUserService.registerTempUser(user);
        return new ResponseEntity<>(userDTO,HttpStatus.CREATED);

    }

    @PostMapping("/login")
    @ApiOperation(value="Login user",notes = "로그인")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
    })
    public ResponseEntity<User> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        UserDTO userDTO= loginUserDTO.toDTO(loginUserDTO);
        if(userDTO.getEmail()==null||userDTO.getPassword()==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = userService.login(userDTO.getEmail(),userDTO.getPassword())
                .orElseThrow(()->new NoSuchDataException("회원 정보가 일치하지 않습니다."));

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/validate/{str}")
    public ResponseEntity validateUser(@PathVariable String str){
        TempUser tempUsermpuser = tempUserService.validateUser(str).orElseThrow(()->new NoSuchDataException("잘못된 url 입니다."));
        User user = tempUsermpuser.toUser();
        userService.registerUser(user);
        tempUserService.deleteTempUser(tempUsermpuser);

        return new ResponseEntity("Success",HttpStatus.OK);

    }

    private String getkey(){
        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        int num=0;
        do{
            num=ran.nextInt(75)+48;
            if((num>=48 && num<=57) || (num>=65 && num<=90) || (num>=97 && num<=122)){
                sb.append((char)num);
            }
            else continue;
        }while(sb.length()<15);
        return sb.toString();
    }
}
