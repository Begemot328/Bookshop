package by.epam.bookshop.validator;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.util.ValidationUtil;

public class ShopValidator implements EntityValidator<Author> {

    private static final String INPUT_ERROR = "error.input";

    @Override
    public void validate(Object... args) throws ValidationException {
        if ( args.length < 3
                || !(args[0] instanceof String)
                || !(args[1] instanceof String)
                || !(((args[2] instanceof String) && ((String) args[2]).isEmpty()
                || ValidationUtil.validateURL((String) args[2]))
                || args[2] == null)
                || ((String) args[0]).isEmpty()
                || ((String) args[1]).isEmpty()) {
            throw new ValidationException(INPUT_ERROR);
        }
    }
}
