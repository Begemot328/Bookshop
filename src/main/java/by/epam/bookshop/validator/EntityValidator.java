package by.epam.bookshop.validator;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.ServiceException;
import by.epam.bookshop.exceptions.ValidationException;

public interface EntityValidator<T extends Entity> {

    public void validate(Object ... args) throws ValidationException;

    public void validate(T t) throws ValidationException;
}
