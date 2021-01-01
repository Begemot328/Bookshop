package by.epam.bookshop.entity.position_action;

import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.exceptions.FactoryException;

import java.time.LocalDateTime;

public class PositionActionFactory implements EntityFactory<PositionAction> {

    @Override
    public PositionAction create(Object... args) throws FactoryException {
        if (args.length < 10
                || !(args[0] instanceof Position)
                || !(args[1] instanceof Position)
                || !(args[2] instanceof User || args[2] == null)
                || !(args[3] instanceof User || args[3] == null)
                || !(args[4] instanceof LocalDateTime)
                || !((args[5]) instanceof Integer)
                || !(args[6] instanceof PositionStatus)
                || !(args[7] instanceof PositionStatus)
                || !(args[8] instanceof Shop)
                ||  !(args[9] instanceof Float)) {
            throw new FactoryException(getWrongDataMessage());
        }
        return new PositionAction((Position) args[0],
                (Position) args[1],
                (User) args[2],
                (User) args[3],
                (LocalDateTime) args[4],
                (Integer) args[5],
                (PositionStatus) args[6],
                (PositionStatus) args[7],
                (Shop) args[8],
                Float.parseFloat(args[9].toString()));
    }
}
