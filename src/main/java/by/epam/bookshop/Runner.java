package by.epam.bookshop;

import by.epam.bookshop.dao.EntityDAO;
import by.epam.bookshop.dao.impl.AuthorDAO;
import by.epam.bookshop.dao.impl.AuthorFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class Runner {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bookshop?useUnicode=true&serverTimezone=UTC", "root", "1234567890");) {

            EntityDAO<Author> dao =  new AuthorDAO(connection);
            System.out.println("all");
            dao.findAll().stream().forEach(System.out::println);

            System.out.println("G");
            dao.findBy(new AuthorFinder()
                    .findByFirstName("G")).stream()
                    .forEach(System.out::println);
            System.out.println("G + M");
            dao.findBy(new AuthorFinder()
                    .findByFirstName("G").findByLastName("M")).stream()
                    .forEach(System.out::println);
            System.out.println("id =1  " + dao.read(1));
        } catch (SQLException | DAOException throwables) {
            throwables.printStackTrace();
        }
    }
}
