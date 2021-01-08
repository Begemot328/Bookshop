package by.epam.bookshop.validator;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.util.ValidationUtil;

import java.net.URL;

public class UserValidator extends AbstractEntityValidator<User> {

    private static final String INPUT_ERROR = "error.input";

    private static final Class[] types = {
            String.class,
            String.class,
            String.class,
            Integer.class,
            AddressObject.class,
            URL.class,
            UserStatus.class
    };

    protected Class[] getTypes() {
        return types;
    }

    @Override
    public void validate(User user) throws ValidationException {
        if (user.getLastName() == null
                || user.getFirstName() == null
                || user.getLogin() == null
                || user.getAddress() == null
                || user.getLastName().isEmpty()
                || user.getFirstName().isEmpty()
                || user.getAddress().getFormattedAddress().isEmpty()) {
            throw new ValidationException(INPUT_ERROR);
        }
        if (user.getLogin().isEmpty()
                || user.getPassword() == 0) {
            throw new ValidationException(INPUT_ERROR);
        }
    }
}
