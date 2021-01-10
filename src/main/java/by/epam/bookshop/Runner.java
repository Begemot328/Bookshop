package by.epam.bookshop;

import by.epam.bookshop.dao.EntityDAO;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.author.MySQLAuthorDAO;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.dao.impl.shop.MySQLShopDAO;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.shop.ShopFactory;
import by.epam.bookshop.exceptions.AddressException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.pool.ConnectionPool;
import by.epam.bookshop.exceptions.ConnectionPoolException;
import by.epam.bookshop.pool.ConnectionProxy;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.util.PasswordCoder;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Runner {
    public static void main(String[] args) throws FactoryException {
        try {
            AddressObject obj = new AddressObject("Минск, Янки Лучины, 11");
            System.out.println(obj.getFormattedAddress());
            System.out.println(new AddressObject(obj.getFormattedAddress()).getFormattedAddress());

            AddressObject obj1 = new AddressObject("Минск, газеты Правда , 26");
            System.out.println(obj1.getFormattedAddress());
            System.out.println(new AddressObject(obj1.getFormattedAddress()).getFormattedAddress());

            AddressObject obj2 = new AddressObject("Минск, Сурганова , 37");
            System.out.println(obj2.getFormattedAddress());
            System.out.println(new AddressObject(obj2.getFormattedAddress()).getFormattedAddress());

            AddressObject obj3 = new AddressObject("Минск, Грушевская , 73");
            System.out.println(obj3.getFormattedAddress());
            System.out.println(new AddressObject(obj3.getFormattedAddress()).getFormattedAddress());
            System.out.println(obj3.getLatitude());
            System.out.println(obj3.getLongitude());
        } catch (AddressException e) {
            e.printStackTrace();
        }
        /*System.out.println(PasswordCoder.code("root"));
        System.out.println(PasswordCoder.code("qwerty"));

        System.out.println("AuthorDAO");
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


        System.out.println("MySQLAuthorDAO");
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {

           EntityDAO<Author> authorDao =  new MySQLAuthorDAO(connection);
            System.out.println("all");
            authorDao.findAll().stream().forEach(System.out::println);

            System.out.println("G");
            authorDao.findBy(new AuthorFinder()
                    .findByFirstName("G")).stream()
                    .forEach(System.out::println);
            System.out.println("G + M");
            authorDao.findBy(new AuthorFinder()
                    .findByFirstName("G").findByLastName("M")).stream()
                    .forEach(System.out::println);
            System.out.println("id =1  " + authorDao.read(1));

            EntityFactory<Author> factory= new AuthorFactory();
            Author author = factory.create("Alexander", "Pushkin");
            authorDao.create(author);

            System.out.println(authorDao.findAll());

            author.setLastName("Poooshkin");
            authorDao.update(author);

            System.out.println(authorDao.findAll());

            if (authorDao.read(author.getId()).equals(author)) {
                System.out.println("OK");
            }
            authorDao.delete(author.getId());

            System.out.println(authorDao.findAll());

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


            EntityFactory<Author> authorFactory= new AuthorFactory();
            Author  author = authorFactory.create("Alexander", "Pushkin");
             authorDao.create(author);

            EntityDAO<Book> bookDAO =  new MySQLBookDAO(connection);

            EntityFactory<Book> bookFactory= new BookFactory();
            Book book = bookFactory.create("The tale about the Golden Rooster",
                    author, "The tale about the Golden Rooster by Alexander Pushkin", (float) 10.1, null);

            bookDAO.create(book);
            System.out.println(bookDAO.findAll());

            book.setDescription("You know it, everybody knows");
            bookDAO.update(book);
            System.out.println(bookDAO.findAll());
            if (bookDAO.read(book.getId()).equals(book)) {
                System.out.println("OK");
            }
            bookDAO.delete(book.getId());
            authorDao.delete(author.getId());
            System.out.println(bookDAO.findAll());

*?
        } catch (SQLException | ConnectionPoolException | DAOException throwables) {
            throwables.printStackTrace();
        }

/*
        for (int i = 0; i< 100; i++) {
            new Thread(new DAOTester("Thread" + i)).start();
        }
        */

    }
}

 class DAOTester implements Runnable {
    private String name;

    public DAOTester(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        ConnectionProxy connection;
        try {
            for (int i = 0; i<10; i++) {
                connection  = (ConnectionProxy) ConnectionPool.getInstance().getConnection();
                EntityDAO<Book> b1 = new MySQLBookDAO(connection);
                System.out.println(LocalDateTime.now() + "  " +  name + "  " + "iteration " + i + " " +  b1.read(1).getTitle());
                //TimeUnit.SECONDS.sleep(1);
                ConnectionPool.getInstance().releaseConnection(connection);
            }

        } catch (ConnectionPoolException | DAOException e) {
            e.printStackTrace();
        }
    }
}
