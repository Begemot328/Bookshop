package by.epam.bookshop.util;

import by.epam.bookshop.exceptions.ServletRuntimeException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordCoder {

   public static int code(String password) {
       return password.hashCode();
   }

    public static String hash(String password)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new ServletRuntimeException(e);
        }
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.US_ASCII));
        return Base64.getEncoder().encodeToString(hashedPassword);
   }


}
