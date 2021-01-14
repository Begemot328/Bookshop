package by.epam.bookshop.entity.user;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.validator.EntityValidator;

public abstract class AbstractEntityFactory<T extends Entity> implements EntityFactory<T> {

    protected abstract EntityValidator<T> getValidator();

    protected abstract T createEntity(Object ... args);

    public T create(Object ... args) throws FactoryException {

        try {
            getValidator().validate(args);
        } catch (ValidationException e) {
            throw new FactoryException(e);
        }
        return createEntity(args);
    }


}
