package by.epam.bookshop.validator;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.ServiceException;

public interface EntityValidator<T extends Entity> {

    public boolean validate(Object ... args);
}
