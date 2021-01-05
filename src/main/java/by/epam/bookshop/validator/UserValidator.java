package by.epam.bookshop.validator;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.util.ValidationUtil;

public class UserValidator implements EntityValidator<User> {

    private static final String INPUT_ERROR = "error.input";

    @Override
    public void validate(Object... args) throws ValidationException {
        if (args.length < 7
                || !(args[0] instanceof String)
                || ((String) args[0]).isEmpty()
                || !(args[1] instanceof String)
                || ((String) args[1]).isEmpty()
                || !(args[2] instanceof String)
                || ((String) args[2]).isEmpty()
                || !(args[3] instanceof Integer)
                || (Integer.parseInt((String) args[3]) <= 0)
                || !(args[4] instanceof String)
                || ((String) args[4]).isEmpty()
                || !((args[5] instanceof String
                && ((String) args[5]).isEmpty() || ValidationUtil.validateURL((String) args[5]))
                || args[5] == null)
                || !(args[6] instanceof UserStatus)) {
            throw new ValidationException(INPUT_ERROR);
        }
    }
}
