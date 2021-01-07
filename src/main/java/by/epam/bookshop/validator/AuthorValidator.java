package by.epam.bookshop.validator;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.util.ValidationUtil;

public class AuthorValidator extends AbstractEntityValidator<Author> {

    private static final String INPUT_ERROR = "error.input";
    private static final String EMPTY_NAME = "error.name.empty";
    private static final String WRONG_LINK = "error.link";
    private static final Class[] types = {
            String.class,
            String.class,
            String.class
    };

    protected Class[] getTypes() {
        return types;
    }

    public void validate(Author author) throws ValidationException {
        if (author.getFirstName() == null
        || author.getFirstName().isEmpty()
        || author.getLastName().isEmpty()) {
            throw new ValidationException(EMPTY_NAME);
        }
        if (ValidationUtil.validateURL(author.getPhotoLink())) {
            throw new ValidationException(WRONG_LINK);
        }
    }
}
