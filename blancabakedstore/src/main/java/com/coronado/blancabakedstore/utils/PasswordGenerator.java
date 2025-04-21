package com.coronado.blancabakedstore.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456"; // <-- Change this for the real password
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Contraseña encriptada: " + encodedPassword);
    }
}
