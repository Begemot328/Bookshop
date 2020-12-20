package by.epam.bookshop.util;

public class PasswordCoder {

   public static int code(String password) {
       return password.hashCode();
   }
}
