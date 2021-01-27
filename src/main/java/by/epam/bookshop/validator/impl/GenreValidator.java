package by.epam.bookshop.validator.impl;

import by.epam.bookshop.entity.genre.Genre;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.validator.AbstractEntityValidator;

public class GenreValidator extends AbstractEntityValidator<Genre> {

    private static final String INPUT_ERROR = "error.input";

    private static final Class[] types = {
            String.class,
    };

    @Override
    protected Class[] getTypes() {
        return types;
    }

    @Override
    public void validate(Genre genre) throws ValidationException {
        if (genre.getName().isEmpty()) {
            throw new ValidationException(INPUT_ERROR);
        }
    }
}
