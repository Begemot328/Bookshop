package by.epam.bookshop.validator.impl;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.validator.AbstractEntityValidator;

import java.net.URL;

public class BookValidator extends AbstractEntityValidator<Book> {

    private static final String INPUT_ERROR = "error.input";

    private static final Class[] types = {
            String.class,
            Author.class,
            String.class,
            Float.class,
            URL.class
    };

    protected Class[] getTypes() {
        return types;
    }


    @Override
    public void validate(Book book) throws ValidationException {
        if (book.getAuthor() == null) {
            throw new ValidationException(INPUT_ERROR);
        }

        if (book.getTitle() == null
        || book.getTitle().isEmpty()) {
            throw new ValidationException(INPUT_ERROR);
        }

        if (book.getPrice() < 0) {
            throw new ValidationException(INPUT_ERROR);
        }

    }

}
