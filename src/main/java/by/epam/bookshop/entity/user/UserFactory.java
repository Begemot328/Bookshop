package by.epam.bookshop.entity.user;

import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.impl.UserValidator;

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
