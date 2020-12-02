package by.epam.bookshop.entity;

import by.epam.bookshop.entity.factory.EntityFactory;
import by.epam.bookshop.exceptions.FactoryException;

import java.time.LocalDateTime;

public class BookActionFactory implements EntityFactory<BookAction> {

    @Override
    public BookAction create(Object... args) throws FactoryException {
        if (args.length < 7
                || !(args[0] instanceof Book)
                || !(args[1] instanceof User)
                || !(args[2] instanceof User)
                || !(args[3] instanceof LocalDateTime)
                || !(Integer.class.isInstance(args[4]))
                || !(args[5] instanceof PositionStatus)
                || !(args[6] instanceof PositionStatus)
                || !(args[7] instanceof Shop)) {
            throw new FactoryException(getWrongDataMessage());
        }
        return new BookAction((Book) args[0],
                (User) args[1], (User) args[2],
                (LocalDateTime) args[3],
                (Integer) args[4],
                (PositionStatus) args[5],
                (PositionStatus) args[6],
                (Shop) args[7]);
    }
}
