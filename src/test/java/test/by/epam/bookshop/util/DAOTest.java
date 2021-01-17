package test.by.epam.bookshop.util;

import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.author.MySQLAuthorDAO;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.dao.impl.book.MySQLBookDAO;
import by.epam.bookshop.dao.impl.position.MySQLPositionDAO;
import by.epam.bookshop.dao.impl.position.PositionFinder;
import by.epam.bookshop.dao.impl.position_action.MySQLPositionActionDAO;
import by.epam.bookshop.dao.impl.position_action.PositionActionFinder;
import by.epam.bookshop.dao.impl.shop.MySQLShopDAO;
import by.epam.bookshop.dao.impl.shop.ShopFinder;
import by.epam.bookshop.dao.impl.user.MySQLUserDAO;
import by.epam.bookshop.dao.impl.user.UserFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionFactory;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.entity.position_action.PositionActionFactory;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.shop.ShopFactory;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserFactory;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.AddressException;
import by.epam.bookshop.exceptions.DAOException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.pool.DbParameter;
import by.epam.bookshop.resource.DbResourceManager;
import by.epam.bookshop.util.AddressObject;
import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class DAOTest extends Assert {

    private static final String USE_UNICODE = "useUnicode";
    private static final String TIMEZONE = "serverTimezone";
    private static final String EQUALS_MARK = "=";
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String DB_URL =
            DbResourceManager.getInstance().getValue(DbParameter.DB_URL);
    private static final String DB_PASSWORD =
            DbResourceManager.getInstance().getValue(DbParameter.DB_PASSWORD);
    private static final String DB_USER =
            DbResourceManager.getInstance().getValue(DbParameter.DB_USER);
    private static final String DB_TIMEZONE =
            DbResourceManager.getInstance().getValue(DbParameter.DB_TIMEZONE);
    private static final String DB_ENCODING =
            DbResourceManager.getInstance().getValue(DbParameter.DB_ENCODING);
    private static final String DB_USE_UNICODE =
            DbResourceManager.getInstance().getValue(DbParameter.DB_USE_UNICODE);
    private static final String DROP_SCHEMA = "drop schema if exists bookshop;";
    private static Map<String, String> dbParameters = new HashMap<>();
    private static Connection connection;
    private static Book book;
    private static Author author;
    private static Shop shop;
    private static User seller;
    private static User buyer;
    private static Position position;
    private static PositionAction positionAction;

    private static MySQLUserDAO userDAO;
    private static MySQLShopDAO shopDAO;
    private static MySQLBookDAO bookDAO;
    private static MySQLAuthorDAO authorDAO;
    private static MySQLPositionDAO positionDAO;
    private static MySQLPositionActionDAO positionActionDAO;

    @BeforeClass
    public static void initDatabase() throws AddressException, ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, SQLException,
            FileNotFoundException, FactoryException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        addDBparameter(TIMEZONE, DB_TIMEZONE);
        addDBparameter(USE_UNICODE, DB_USE_UNICODE);
        System.out.println(getDbUrl());
        connection = DriverManager.getConnection(
                getDbUrl(), DB_USER, DB_PASSWORD);
        Scanner scanner = new Scanner(new FileReader(new File("SQL/bookshopTest.sql")));
        scanner.useDelimiter(";");
        Statement statement = connection.createStatement();
        while (scanner.hasNext()) {
            statement.addBatch(scanner.next().concat(";"));
        }
        statement.executeBatch();
        shop = new ShopFactory().create("На Сурганова", new AddressObject("Минск, Сурганова, 11"), null);
        author = new AuthorFactory().create("Alexander", "Pushkin", null, null);
        book = new BookFactory().create("The tale about the Golden Rooster",
                author, "The tale about the Golden Rooster by Alexander Pushkin", (float) 10.1, null);
        seller = new UserFactory().create("Yury", "Zmushko", "login", -946852072,
                new AddressObject("Минск, Дзержинского, 11"), null, UserStatus.SELLER);
        buyer = new UserFactory().create("Yury", "Zmushko", "login", -946852072,
                new AddressObject("Минск, Дзержинского, 11"), null, UserStatus.SELLER);
        position = new PositionFactory().create(book, shop, PositionStatus.READY, null, 1);
        positionAction = new PositionActionFactory().create(null, position, buyer, seller, LocalDateTime.now(),
                1, PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, book.getPrice());

        userDAO = new MySQLUserDAO(connection);
        shopDAO = new MySQLShopDAO(connection);
        bookDAO = new MySQLBookDAO(connection);
        authorDAO = new MySQLAuthorDAO(connection);
        positionDAO = new MySQLPositionDAO(connection);
        positionActionDAO = new MySQLPositionActionDAO(connection);
    }

    @AfterClass
    public static void clearDatabase() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DROP_SCHEMA);
        statement.executeUpdate();
        connection.close();
    }

    @Test
    public void testShopDao() throws AddressException, DAOException {
        MySQLShopDAO shopDAO = new MySQLShopDAO(connection);

        // ShopDAO block test
        // Create
        assertTrue(shopDAO.findAll().isEmpty());
        assertEquals(shopDAO.countAll(), 0);
        shopDAO.create(shop);
        assertFalse(shopDAO.findAll().isEmpty());
        assertEquals(shopDAO.countAll(), 1);

        // Read
        assertEquals(shop, shopDAO.read(shop.getId()));

        // Find and count
        assertTrue(shopDAO.findAll().contains(shop));
        assertTrue(shopDAO.findBy(new ShopFinder()
                .findByName(shop.getName())).contains(shop));
        assertTrue(shopDAO.findBy(new ShopFinder()
                .findByAdress((shop.getAddress().toString()))).contains(shop));
        assertFalse(shopDAO.findBy(new ShopFinder()
                .findByName("Ivan")).contains(shop));
        assertFalse(shopDAO.findBy(new ShopFinder()
                .findByAdress(new AddressObject("Minsk, pr.Nezavasimosti, 65").getFormattedAddress())).contains(shop));

        // Update
        shop.setName("Gogolevsky");
        shopDAO.update(shop);
        assertEquals(shopDAO.read(shop.getId()), shop);

        // Delete
        shopDAO.delete(shop.getId());
        assertFalse(shopDAO.findAll().contains(shop));
    }

    @Test
    public void testPositionDao() throws AddressException, DAOException {

        userDAO.create(buyer);
        userDAO.create(seller);
        shopDAO.create(shop);
        authorDAO.create(author);
        bookDAO.create(book);

        // Create
        assertTrue(positionDAO.findAll().isEmpty());
        assertEquals(positionDAO.countAll(), 0);
        positionDAO.create(position);
        assertFalse(positionDAO.findAll().isEmpty());
        assertEquals(positionDAO.countAll(), 1);

        // Read
        assertEquals(positionDAO.read(position.getId()), position);
        assertNull(positionDAO.read(10));

        // Find and count
        assertTrue(positionDAO.findAll().contains(position));
        assertEquals(positionDAO.countAll(), 1);

        assertTrue(positionDAO.findBy(new PositionFinder().findByShop(shop.getId())).contains(position));
        assertEquals(positionDAO.countBy(new PositionFinder().findByShop(shop.getId())), 1);
        assertFalse(positionDAO.findBy(new PositionFinder().findByShop(10)).contains(position));

        assertTrue(positionDAO.findBy(new PositionFinder().findByStatus(
                PositionStatus.READY.getId())).contains(position));
        assertEquals(positionDAO.countBy(new PositionFinder().findByStatus(
                PositionStatus.READY.getId())), 1);
        assertFalse(positionDAO.findBy(new PositionFinder().findByStatus(
                PositionStatus.SOLD.getId())).contains(position));

        assertTrue(positionDAO.findBy(new PositionFinder().findByBook(
                book.getId())).contains(position));
        assertEquals(positionDAO.countBy(new PositionFinder().findByBook(
                book.getId())), 1);
        assertFalse(positionDAO.findBy(new PositionFinder().findByBook(
                3)).contains(position));

        assertTrue(positionDAO.findBy(new PositionFinder().findByQuantity(
                position.getQuantity())).contains(position));
        assertEquals(positionDAO.countBy(new PositionFinder().findByQuantity(
                position.getQuantity())), 1);
        assertFalse(positionDAO.findBy(new PositionFinder().findByQuantity(
                10)).contains(position));

        assertTrue(positionDAO.findBy(new PositionFinder().findByQuantityLess(2)
                .findByQuantityMore(0)).contains(position));
        assertEquals(positionDAO.countBy(new PositionFinder()
                .findByQuantityLess(2).findByQuantityMore(0)), 1);
        assertFalse(positionDAO.findBy(new PositionFinder()
                .findByQuantityLess(5).findByQuantityMore(2)).contains(position));

        // Update
        position.setStatus(PositionStatus.SOLD);
        positionDAO.update(position);
        assertEquals(positionDAO.read(position.getId()), position);

        // Delete
        positionDAO.delete(position.getId());
        assertTrue(positionDAO.findAll().isEmpty());

        //Clear
        userDAO.delete(buyer.getId());
        userDAO.delete(seller.getId());
        bookDAO.delete(book.getId());
        authorDAO.delete(author.getId());
        shopDAO.delete(shop.getId());
    }

    @Test
    public void testPositionActionDao() throws AddressException, DAOException {

        userDAO.create(buyer);
        userDAO.create(seller);
        shopDAO.create(shop);
        authorDAO.create(author);
        bookDAO.create(book);
        positionDAO.create(position);

        // Create
        assertTrue(positionActionDAO.findAll().isEmpty());
        assertEquals(positionActionDAO.countAll(), 0);
        positionActionDAO.create(positionAction);
        assertFalse(positionActionDAO.findAll().isEmpty());
        assertEquals(positionActionDAO.countAll(), 1);

        // Read
        assertEquals(positionActionDAO.read(positionAction.getId()), positionAction);
        assertNull(positionActionDAO.read(10));

        // Find and count
        assertTrue(positionActionDAO.findAll().contains(positionAction));
        assertEquals(positionActionDAO.countAll(), 1);

        assertTrue(positionActionDAO.findBy(new PositionActionFinder().findByShop(shop.getId())).contains(positionAction));
        assertEquals(positionActionDAO.countBy(new PositionActionFinder().findByShop(shop.getId())), 1);
        assertFalse(positionActionDAO.findBy(new PositionActionFinder().findByShop(10)).contains(positionAction));

        assertTrue(positionActionDAO.findBy(new PositionActionFinder().findByFinalStatus(
                PositionStatus.READY.getId())).contains(positionAction));
        assertEquals(positionActionDAO.countBy(new PositionActionFinder().findByFinalStatus(
                PositionStatus.READY.getId())), 1);
        assertFalse(positionActionDAO.findBy(new PositionActionFinder().findByFinalStatus(
                PositionStatus.SOLD.getId())).contains(positionAction));

        assertTrue(positionActionDAO.findBy(new PositionActionFinder().findByInitialStatus(
                PositionStatus.NON_EXISTENT.getId())).contains(positionAction));
        assertEquals(positionActionDAO.countBy(new PositionActionFinder().findByInitialStatus(
                PositionStatus.NON_EXISTENT.getId())), 1);
        assertFalse(positionActionDAO.findBy(new PositionActionFinder().findByInitialStatus(
                PositionStatus.SOLD.getId())).contains(positionAction));

        assertTrue(positionActionDAO.findBy(new PositionActionFinder().findByDateEarlier(
                LocalDateTime.now().plusMinutes(10))).contains(positionAction));
        assertEquals(positionActionDAO.countBy(new PositionActionFinder().findByDateEarlier(
                LocalDateTime.now().plusMinutes(10))), 1);
        assertFalse(positionActionDAO.findBy(new PositionActionFinder().findByDateEarlier(
                LocalDateTime.now().minusMinutes(10))).contains(positionAction));

        assertTrue(positionActionDAO.findBy(new PositionActionFinder().findByDateLater(
                LocalDateTime.now().minusMinutes(10))).contains(positionAction));
        assertEquals(positionActionDAO.countBy(new PositionActionFinder().findByDateLater(
                LocalDateTime.now().minusMinutes(10))), 1);
        assertFalse(positionActionDAO.findBy(new PositionActionFinder().findByDateLater(
                LocalDateTime.now().plusMinutes(10))).contains(positionAction));

        assertTrue(positionActionDAO.findBy(new PositionActionFinder().findByQuantity(
                positionAction.getQuantity())).contains(positionAction));
        assertEquals(positionActionDAO.countBy(new PositionActionFinder().findByQuantity(
                positionAction.getQuantity())), 1);
        assertFalse(positionActionDAO.findBy(new PositionActionFinder().findByQuantity(
                10)).contains(positionAction));

        assertTrue(positionActionDAO.findBy(new PositionActionFinder().findByQuantityLess(2)
                .findByQuantityMore(0)).contains(positionAction));
        assertEquals(positionActionDAO.countBy(new PositionActionFinder()
                .findByQuantityLess(2).findByQuantityMore(0)), 1);
        assertFalse(positionActionDAO.findBy(new PositionActionFinder()
                .findByQuantityLess(5).findByQuantityMore(2)).contains(positionAction));

        // Update
        positionAction.setFinalStatus(PositionStatus.SOLD);
        positionActionDAO.update(positionAction);
        assertEquals(positionActionDAO.read(positionAction.getId()), positionAction);

        // Delete
        positionActionDAO.delete(positionAction.getId());
        assertTrue(positionActionDAO.findAll().isEmpty());

        //Clear
        userDAO.delete(buyer.getId());
        userDAO.delete(seller.getId());
        bookDAO.delete(book.getId());
        authorDAO.delete(author.getId());
        shopDAO.delete(shop.getId());
        positionDAO.delete(position.getId());
    }
    
    @Test
    public void testUserDao() throws AddressException, DAOException {
        // UserDAO block test
        // Create
        assertTrue(userDAO.findAll().isEmpty());
        assertEquals(userDAO.countAll(), 0);
        userDAO.create(seller);
        assertFalse(userDAO.findAll().isEmpty());
        assertEquals(userDAO.countAll(), 1);

        // Read
        assertEquals(seller, userDAO.read(seller.getId()));

        // Find and count
        assertTrue(userDAO.findAll().contains(seller));
        assertTrue(userDAO.findBy(new UserFinder()
                .findByFirstName(seller.getFirstName())).contains(seller));
        assertTrue(userDAO.findBy(new UserFinder()
                .findByLastName((seller.getLastName()))).contains(seller));
        assertTrue(userDAO.findBy(new UserFinder()
                .findByLogin((seller.getLogin()))).contains(seller));
        assertTrue(userDAO.findBy(new UserFinder()
                .findByStatus((seller.getStatus()))).contains(seller));
        assertFalse(userDAO.findBy(new UserFinder()
                .findByFirstName("Ivan")).contains(seller));
        assertFalse(userDAO.findBy(new UserFinder()
                .findByLastName(("Ivanov"))).contains(seller));
        assertFalse(userDAO.findBy(new UserFinder()
                .findByLogin("dfgdg")).contains(seller));
        assertTrue(userDAO.findBy(new UserFinder()
                .findByStatus(UserStatus.SELLER)).contains(seller));
        assertFalse(userDAO.findBy(new UserFinder()
                .findByStatus(UserStatus.BUYER)).contains(seller));
        assertEquals(userDAO.countBy(new UserFinder()
                .findByLastName(("Ivanov"))), 0);
        assertEquals(userDAO.countBy(new UserFinder()
                .findByLastName((seller.getLastName()))), 1);
        assertFalse(userDAO.findBy(new UserFinder()
                .findByLastName(("Ivanov"))).contains(seller));

        // Update
        seller.setFirstName("John");
        userDAO.update(seller);
        assertEquals(userDAO.read(seller.getId()), seller);

        // Delete
        userDAO.delete(seller.getId());
        assertFalse(userDAO.findAll().contains(seller));
    }
    
    @Test
    public void testAuthorDao() throws AddressException, DAOException {

        // AuthorDAO block test
        // Create
        assertTrue(authorDAO.findAll().isEmpty());
        assertEquals(authorDAO.countAll(), 0);
        authorDAO.create(author);
        assertFalse(authorDAO.findAll().isEmpty());
        assertEquals(authorDAO.countAll(), 1);

        // Read
        assertEquals(author, authorDAO.read(author.getId()));

        // Find and count
        assertTrue(authorDAO.findAll().contains(author));
        assertTrue(authorDAO.findBy(new AuthorFinder()
                .findByFirstName(author.getFirstName())).contains(author));
        assertTrue(authorDAO.findBy(new AuthorFinder()
                .findByLastName((author.getLastName()))).contains(author));
        assertFalse(authorDAO.findBy(new AuthorFinder()
                .findByFirstName("Ivan")).contains(author));
        assertFalse(authorDAO.findBy(new AuthorFinder()
                .findByLastName(("Ivanov"))).contains(author));
        assertEquals(authorDAO.countBy(new AuthorFinder()
                .findByLastName(("Ivanov"))), 0);
        assertEquals(authorDAO.countBy(new AuthorFinder()
                .findByLastName((author.getLastName()))), 1);

        // Update
        author.setFirstName("Gogol");
        authorDAO.update(author);
        assertEquals(authorDAO.read(author.getId()), author);

        // Delete
        authorDAO.delete(author.getId());
        assertFalse(authorDAO.findAll().contains(author));
    }

    @Test
    public void testBookDao() throws AddressException, DAOException {

        // Add author
        authorDAO.create(author);
        // BookDAO block test
        // Create
        assertEquals(bookDAO.countAll(), 0);
        bookDAO.create(book);
        assertEquals(bookDAO.countAll(), 1);

        // Read
        assertEquals(book, bookDAO.read(book.getId()));

        // Find and count
        assertTrue(bookDAO.findAll().contains(book));
        assertEquals(Collections.singletonList(book), bookDAO.findBy(
                new BookFinder().findByTitle(book.getTitle())));
        assertTrue(bookDAO.findBy(
                new BookFinder().findByTitle(book.getTitle().concat("qwerty"))).isEmpty());

        assertEquals(Collections.singletonList(book), bookDAO.findBy(
                new BookFinder().findByAuthor(author.getId())));
        assertTrue(bookDAO.findBy(
                new BookFinder().findByAuthor(10)).isEmpty());

        assertTrue(bookDAO.findBy(
                new BookFinder().findByPriceLess(book.getPrice() - 0.01f)).isEmpty());
        assertTrue(bookDAO.findBy(
                new BookFinder().findByPriceMore(book.getPrice() + 0.01f)).isEmpty());
        assertEquals(Collections.singletonList(book),
                bookDAO.findBy(new BookFinder().findByPriceLess(
                book.getPrice() + 0.01f).findByPriceMore(book.getPrice() - 0.01f)));
        assertEquals(bookDAO.countBy(
                new BookFinder().findByPriceLess(
                book.getPrice() + 0.01f).findByPriceMore(book.getPrice() - 0.01f)), 1);

        // Update
        book.setTitle("qwerty");
        bookDAO.update(book);
        assertEquals(bookDAO.read(book.getId()), book);

        // Delete
        bookDAO.delete(book.getId());
        assertFalse(bookDAO.findAll().contains(book));

        // Cleaning
        authorDAO.delete(author.getId());

    }

    private static void addDBparameter(String parameterName, String value) {
        if (parameterName != null && !parameterName.isEmpty()
                && value != null && !value.isEmpty()) {
            dbParameters.put(parameterName, value);
        }
    }

    private static String getDbUrl() {
        String result = DB_URL;
        Set<String> keyset = dbParameters.keySet();

        if (keyset.size() == 0) {
            return result;
        }

        result = result.concat(QUESTION_MARK);

        for (String key : keyset) {
            result = result.concat(
                    key + EQUALS_MARK + dbParameters.get(key) + AMPERSAND);
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }
}
