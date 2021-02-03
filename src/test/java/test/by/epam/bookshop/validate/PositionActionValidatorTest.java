package test.by.epam.bookshop.validate;

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
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.validator.impl.PositionActionValidator;
import by.epam.bookshop.validator.impl.PositionValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;

public class PositionActionValidatorTest extends Assert {
    EntityValidator<PositionAction>  validator;
    URL url;
    Author author;
    Book book;
    User seller;
    User buyer;
    Position initialPosition;
    Position finalPosition;
    PositionAction positionAction;
    Shop shop;

    @Before
    public void init() throws MalformedURLException, FactoryException, AddressException {
        url = new URL("http://www.google.com");
        validator = new PositionActionValidator();
        author = new AuthorFactory().create("Alexander", "Pushkin", "description", url);
        book = new BookFactory().create("The tale about the Golden Rooster",
                author, "The tale about the Golden Rooster by Alexander Pushkin", (float) 10.1, url);
        seller = new UserFactory().create("Yury", "Zmushko", "login", "946852072",
                new AddressObject("Минск, Дзержинского, 11"), null, UserStatus.SELLER);
        buyer = new UserFactory().create("Yury", "Zmushko", "login", "946852072",
                new AddressObject("Минск, Дзержинского, 11"), null, UserStatus.SELLER);
        shop = new ShopFactory().create("На Сурганова", new AddressObject("Минск, Сурганова, 11"), null);
        initialPosition = new PositionFactory().create(book, shop, PositionStatus.READY, "note", 1);

        finalPosition = new PositionFactory().create(book, shop, PositionStatus.READY, "note", 1);
        positionAction = new PositionActionFactory()
                .create(initialPosition, finalPosition, buyer, seller, LocalDateTime.now(), 1,
                        PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 10.4f);
    }

    @Test
    public void validatePositionActionTest() throws ValidationException {
        validator.validate(positionAction);

        positionAction.setInitialPosition(null);
        validator.validate(positionAction);

        positionAction.setSeller(null);
        validator.validate(positionAction);

        positionAction.setBuyer(null);
        validator.validate(positionAction);

    }

    @Test (expected = ValidationException.class)
    public void validatePositionAction1Test() throws ValidationException {
        positionAction.setFinalPosition(null);
        validator.validate(positionAction);
    }
    
    @Test (expected = ValidationException.class)
    public void validatePositionAction2Test() throws ValidationException {
        positionAction.setCurrentPrice(0);
        validator.validate(positionAction);
    }

    @Test (expected = ValidationException.class)
    public void validatePositionAction3Test() throws ValidationException {
        positionAction.setCurrentPrice(-1);
        validator.validate(positionAction);
    }

    @Test (expected = ValidationException.class)
    public void validatePositionAction4Test() throws ValidationException {
        positionAction.setShop(null);
        validator.validate(positionAction);
    }

    @Test (expected = ValidationException.class)
    public void validatePositionAction5Test() throws ValidationException {
        positionAction.setFinalStatus(null);
        validator.validate(positionAction);
    }

    @Test (expected = ValidationException.class)
    public void validatePositionAction6Test() throws ValidationException {
        positionAction.setInitialStatus(null);
        validator.validate(positionAction);
    }

    @Test (expected = ValidationException.class)
    public void validatePositionAction7Test() throws ValidationException {
        positionAction.setQuantity(0);
        validator.validate(positionAction);
    }

    @Test (expected = ValidationException.class)
    public void validatePositionAction8Test() throws ValidationException {
        positionAction.setQuantity(-1);
        validator.validate(positionAction);
    }

    @Test
    public void validateArgsTest() throws ValidationException {
        validator.validate(initialPosition, finalPosition, buyer, seller, LocalDateTime.now(), 1,
                        PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 10.4f);
        validator.validate(null, finalPosition, buyer, seller, LocalDateTime.now(), 1,
                PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 10.4f);
        validator.validate(initialPosition, finalPosition, null, seller, LocalDateTime.now(), 1,
                PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 10.4f);
        validator.validate(initialPosition, finalPosition, buyer, null, LocalDateTime.now(), 1,
                PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 10.4f);
        validator.validate(null, finalPosition, buyer, seller, LocalDateTime.now(), 1,
                PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 10.4f);
    }
    
    @Test (expected = ValidationException.class)
    public void validateArgs1Test() throws ValidationException {
        validator.validate(buyer, seller, initialPosition, finalPosition,  LocalDateTime.now(), 1,
                PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 10.4f);
    }



    @Test (expected = ValidationException.class)
    public void validateArgs4Test() throws ValidationException {
        validator.validate(initialPosition, finalPosition, buyer, seller, LocalDateTime.now(), 1,
                PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, true);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs5Test() throws ValidationException {
        validator.validate(initialPosition, finalPosition, buyer, seller, LocalDateTime.now(), 1,
                PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 11L);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs6Test() throws ValidationException {
        validator.validate(initialPosition, finalPosition, buyer, seller, new Date(1000), 1,
                PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 11f);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs7Test() throws ValidationException {
        validator.validate(initialPosition, finalPosition, buyer, seller, LocalDateTime.now(),
                PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 11f);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs9Test() throws ValidationException {
        validator.validate(initialPosition, finalPosition, buyer, seller, LocalDateTime.now(), 2.0,
                PositionStatus.NON_EXISTENT, PositionStatus.READY, shop, 11f);
    }
}
