package com.codecool.shop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordSecurity {

    public static String getSecurePassword(String password, byte[] salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

//    public static String hashPassword(String password) throws NoSuchAlgorithmException {
//        byte[] salt = getSalt();
//        return getSecurePassword(password, salt);
//    }
        // same salt should be passed
//        byte[] salt = getSalt();
//        String password1 = getSecurePassword("Password", salt);
//        String password2 = getSecurePassword("Password", salt);
//        System.out.println(" Password 1 -> " + password1);
//        System.out.println(" Password 2 -> " + password2);


}