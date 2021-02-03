package by.epam.bookshop.validator.impl;

import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.validator.AbstractEntityValidator;

import java.time.LocalDateTime;

public class PositionActionValidator extends AbstractEntityValidator<PositionAction> {
    private static final String INPUT_ERROR = "error.input";

    private static final Class[] types = {
            Position.class,
            Position.class,
            User.class,
            User.class,
            LocalDateTime.class,
            Integer.class,
            PositionStatus.class,
            PositionStatus.class,
            Shop.class,
            Float.class
    };

    protected Class[] getTypes() {
        return types;
    }

    @Override
    public void validate(PositionAction positionAction) throws ValidationException {
        if (positionAction.getShop() == null
                || positionAction.getFinalPosition() == null
                || positionAction.getCurrentPrice() <= 0
                || positionAction.getQuantity() <= 0
                || positionAction.getInitialStatus() == null
                || positionAction.getFinalStatus() == null) {
            throw new ValidationException(INPUT_ERROR);
        }
    }
}
