package by.epam.bookshop.validator;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.util.ValidationUtil;
import by.epam.bookshop.validator.EntityValidator;

public class PositionValidator extends AbstractEntityValidator<Position> {
    private static final String INPUT_ERROR = "error.input";

    private static final Class[] types = {
            Book.class,
            Shop.class,
            PositionStatus.class,
            String.class,
            Integer.class
    };

    protected Class[] getTypes() {
        return types;
    }

    @Override
    public void validate(Position position) throws ValidationException {
        if (position.getBook() == null
        || position.getStatus() == null
        || position.getShop() == null
        || position.getQuantity() < 0) {
            throw new ValidationException(INPUT_ERROR);
        }
    }
}
