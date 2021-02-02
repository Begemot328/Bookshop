package test.by.epam.bookshop.util;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionFactory;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.shop.ShopFactory;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserFactory;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.AddressException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.validator.impl.BookValidator;
import by.epam.bookshop.validator.impl.PositionValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class PositionValidatorTest extends Assert {
    EntityValidator<Position>  validator;
    URL url;
    Author author;
    Book book;
    User seller;
    User buyer;
    Position position;
    Shop shop;

    @Before
    public void init() throws MalformedURLException, FactoryException, AddressException {
        url = new URL("http://www.google.com");
        validator =  new PositionValidator();
        author = new AuthorFactory().create("Alexander", "Pushkin", "description", url);
        book = new BookFactory().create("The tale about the Golden Rooster",
                author, "The tale about the Golden Rooster by Alexander Pushkin", (float) 10.1, url);
        seller = new UserFactory().create("Yury", "Zmushko", "login", -946852072,
                new AddressObject("Минск, Дзержинского, 11"), null, UserStatus.SELLER);
        buyer = new UserFactory().create("Yury", "Zmushko", "login", -946852072,
                new AddressObject("Минск, Дзержинского, 11"), null, UserStatus.SELLER);
        shop = new ShopFactory().create("На Сурганова", new AddressObject("Минск, Сурганова, 11"), null);
        position = new PositionFactory().create(book, shop, PositionStatus.READY, "note", (Integer) 1);
    }

    @Test
    public void validatePositionTest() throws ValidationException {
        validator.validate(position);

        position.setNote("");
        validator.validate(position);

        position.setNote(null);
        validator.validate(position);
    }

    @Test (expected = ValidationException.class)
    public void validatePosition1Test() throws ValidationException {
        position.setBook(null);
        validator.validate(position);
    }

    @Test (expected = ValidationException.class)
    public void validatePosition2Test() throws ValidationException {
        position.setShop(null);
        validator.validate(position);
    }

    @Test (expected = ValidationException.class)
    public void validatePosition3Test() throws ValidationException {
        position.setQuantity(-1);
        validator.validate(position);
    }

    @Test (expected = ValidationException.class)
    public void validatePosition4Test() throws ValidationException {
        position.setQuantity(-1);
        validator.validate(position);
    }

    @Test (expected = ValidationException.class)
    public void validatePosition5Test() throws ValidationException {
        position.setStatus(null);
        validator.validate(position);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs1Test() throws ValidationException {
        validator.validate(book, shop, PositionStatus.READY,  (Integer) 1, "note");
    }

    @Test (expected = ValidationException.class)
    public void validateArgs2Test() throws ValidationException {
        validator.validate(book, PositionStatus.READY, "note",  (Integer) 1);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs3Test() throws ValidationException {
        validator.validate(book, shop, PositionStatus.READY, "note");
    }

    @Test (expected = ValidationException.class)
    public void validateArgs4Test() throws ValidationException {
        validator.validate(shop, book, PositionStatus.READY, "note",  (Integer) 1);
    }

    @Test
    public void validateArgsTest() throws ValidationException {
        validator.validate(book, shop, PositionStatus.READY, "note",  (Integer) 1);
        validator.validate(book, shop, PositionStatus.READY, "",  (Integer) 1);
        validator.validate(book, shop, PositionStatus.READY, null,  (Integer) 1);
    }
}
