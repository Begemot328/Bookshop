package by.epam.bookshop.entity.position;

import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.FactoryException;

public class PositionFactory implements EntityFactory<Position> {

    @Override
    public Position create(Object... args) throws FactoryException {
        if (args.length < 5
                || !(args[0] instanceof Book)
                || !(args[1] instanceof Shop)
                || !(args[2] instanceof PositionStatus)
                || !(args[3] instanceof String || args[3] == null)
                || !((args[4]) instanceof Integer)) {
            throw new FactoryException(getWrongDataMessage());
        }
        return new Position((Book) args[0],
                (Shop) args[1], (PositionStatus) args[2],
                (String) args[3],
                (Integer) args[4]);
    }
}
