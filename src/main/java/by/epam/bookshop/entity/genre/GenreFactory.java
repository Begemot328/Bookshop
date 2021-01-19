package by.epam.bookshop.entity.genre;

import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.validator.impl.GenreValidator;

public class GenreFactory extends AbstractEntityFactory<Genre> {
    @Override
    protected EntityValidator<Genre> getValidator() {
        return new GenreValidator();
    }

    @Override
    protected Genre createEntity(Object... args) {
        return new Genre((String) args[0]);
    }
}
