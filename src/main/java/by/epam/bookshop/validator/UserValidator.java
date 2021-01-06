package by.epam.bookshop.validator;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.util.ValidationUtil;

public class UserValidator extends AbstractEntityValidator<User> {

    private static final String INPUT_ERROR = "error.input";

    private static final Class[] types = {
            String.class,
            String.class,
            String.class,
            Integer.class,
            String.class,
            String.class,
            UserStatus.class
    };

    protected Class[] getTypes() {
        return types;
    }

    @Override
    public void validate(User user) throws ValidationException {
        if (user.getLastName().isEmpty()
        || user.getFirstName().isEmpty()
                || user.getAdress().isEmpty()) {
            throw new ValidationException(INPUT_ERROR);
        }
        if (user.getLogin().isEmpty()
        || user.getPassword() == 0) {
            throw new ValidationException(INPUT_ERROR);
        }
        if (!user.getPhotoLink().isEmpty()
                && !ValidationUtil.validateURL(user.getPhotoLink())) {
            throw new ValidationException(INPUT_ERROR);
        }
    }
}
