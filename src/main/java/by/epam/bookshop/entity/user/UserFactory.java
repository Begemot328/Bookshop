package by.epam.bookshop.entity.user;

import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.AuthorValidator;
import by.epam.bookshop.validator.UserValidator;

import java.net.URL;

public class UserFactory extends AbstractEntityFactory<User> {

    public UserValidator getValidator() {
        return new UserValidator();
    }

    @Override
    protected User createEntity(Object... args) {
        return new User((String) args[0],
                (String) args[1],
                (String) args[2],
                (Integer) args[3],
                (AddressObject) args[4],
                (URL) args[5],
                (UserStatus) args[6]);
    }
}
