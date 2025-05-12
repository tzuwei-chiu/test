package com.example.common;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String originPassword) {
        return BCrypt.hashpw(originPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String originPassword, String hashedPassword) {
        return BCrypt.checkpw(originPassword, hashedPassword);
    }
}