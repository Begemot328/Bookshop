package by.epam.bookshop.entity.book_action;

import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.exceptions.FactoryException;

import java.time.LocalDateTime;

public class BookActionFactory implements EntityFactory<BookAction> {

    @Override
    public BookAction create(Object... args) throws FactoryException {
        if (args.length < 9
                || !(args[0] instanceof Book)
                || !(args[1] instanceof User)
                || !(args[2] instanceof User)
                || !(args[3] instanceof LocalDateTime)
                || !(Integer.class.isInstance(args[4]))
                || !(args[5] instanceof PositionStatus)
                || !(args[6] instanceof PositionStatus)
                || !(args[7] instanceof Shop)
                ||  !(args[8] instanceof Double)) {
            throw new FactoryException(getWrongDataMessage());
        }
        return new BookAction((Book) args[0],
                (User) args[1],
                (User) args[2],
                (LocalDateTime) args[3],
                (Integer) args[4],
                (PositionStatus) args[5],
                (PositionStatus) args[6],
                (Shop) args[7],
                Float.parseFloat(args[8].toString()));
    }
}
