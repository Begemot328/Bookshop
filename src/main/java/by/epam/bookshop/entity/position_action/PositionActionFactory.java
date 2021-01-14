package by.epam.bookshop.entity.position_action;

import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.validator.impl.PositionActionValidator;

import java.time.LocalDateTime;

public class PositionActionFactory extends AbstractEntityFactory<PositionAction> {

    public PositionActionValidator getValidator() {
        return new PositionActionValidator();
    }

    @Override
    protected PositionAction createEntity(Object... args) {
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
