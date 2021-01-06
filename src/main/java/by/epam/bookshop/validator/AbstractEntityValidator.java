package by.epam.bookshop.validator;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.ValidationException;

public abstract class AbstractEntityValidator<T extends Entity> implements EntityValidator<T>{
    private static final String INPUT_ERROR = "error.input";

    @Override
    public void validate(Object... args) throws ValidationException {
        if (args.length != getTypes().length) {
            throw new ValidationException(INPUT_ERROR);
        }
        for (int i = 0; i < args.length; i++) {
            if (!(args[i] == null || getTypes()[i].isInstance(args[i]))) {
                throw new ValidationException(INPUT_ERROR);
            }
        }
    }

    protected abstract Class[] getTypes();
}
