package by.epam.bookshop;

import by.epam.bookshop.dao.EntityDAO;
import by.epam.bookshop.dao.impl.author.MySQLAuthorDAO;
import by.epam.bookshop.dao.impl.shop.MySQLShopDAO;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.shop.ShopFactory;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Runner {
    public static void main(String[] args) throws FactoryException {
      /*  System.out.println("AuthorDAO");
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

*/
        System.out.println("MySQLAuthorDAO");
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bookshop?useUnicode=true&serverTimezone=UTC", "root", "1234567890");) {

           EntityDAO<Author> dao =  new MySQLAuthorDAO(connection);
 /*            System.out.println("all");
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

            EntityFactory<Author> factory= new AuthorFactory();
            Author author = factory.create("Alexander", "Pushkin");
            dao.create(author);

            System.out.println(dao.findAll());

            author.setLastName("Poooshkin");
            dao.update(author);

            System.out.println(dao.findAll());

            if (dao.read(author.getId()).equals(author)) {
                System.out.println("OK");
            }
            dao.delete(author.getId());

            System.out.println(dao.findAll());
*/
            MySQLShopDAO shopDAO = new MySQLShopDAO(connection);
            ShopFactory shopFactory = new ShopFactory();

            System.out.println(shopDAO.findAll());
            Shop shop = shopFactory.create("Na pushkinskoj", "St. m. Pushkinskaya", "position");
            shopDAO.create(shop);
            System.out.println(shopDAO.findAll());

            shop.setName("Pushka");
            shopDAO.update(shop);
            System.out.println(shopDAO.findAll());
            if (shopDAO.read(shop.getId()).equals(shop)) {
                System.out.println("OK");
            }

            shopDAO.delete(shop.getId());

        } catch (SQLException | DAOException throwables) {
            throwables.printStackTrace();
        }
    }
}
