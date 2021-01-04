package by.epam.bookshop.validator;

import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.validator.EntityValidator;

public class PositionValidator implements EntityValidator<Position> {
    @Override
    public boolean validate(Object... args) {
        return !(args.length < 5
                || !(args[0] instanceof Book)
                || !(args[1] instanceof Shop)
                || !(args[2] instanceof PositionStatus)
                || !(args[3] instanceof String || args[3] == null)
                || !((args[4]) instanceof Integer));
    }
}
