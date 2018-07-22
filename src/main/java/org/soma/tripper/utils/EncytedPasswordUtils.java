package org.soma.tripper.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncytedPasswordUtils {
    public static String encytedPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        String password="123";
        String ps = encytedPassword(password);
        System.out.println("PassWord = "+ps);
    }
}
