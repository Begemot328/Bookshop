package by.epam.bookshop.validator;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.ValidationException;

public interface EntityValidator<T extends Entity> {

    void validate(Object... args) throws ValidationException;

    void validate(T t) throws ValidationException;
}
