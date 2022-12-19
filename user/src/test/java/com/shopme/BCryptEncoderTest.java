package com.shopme;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BCryptEncoderTest {

    @Test
    public void testBcryptEncoder(){

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("test234");
        boolean isMatches = bCryptPasswordEncoder.matches("test234", encodedPassword);
        assertTrue(isMatches);

    }
}
