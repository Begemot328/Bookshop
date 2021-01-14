package by.epam.bookshop.entity.position;

import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.validator.impl.PositionValidator;

public class PositionFactory extends AbstractEntityFactory<Position> {

    public PositionValidator getValidator() {
        return new PositionValidator();
    }

    @Override
    protected Position createEntity(Object... args) {
        return new Position((Book) args[0],
                (Shop) args[1], (PositionStatus) args[2],
                (String) args[3],
                (Integer) args[4]);
    }
}
