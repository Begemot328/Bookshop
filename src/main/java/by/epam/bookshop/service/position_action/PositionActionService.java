package by.epam.bookshop.service.position_action;

import by.epam.bookshop.dao.impl.position_action.MySQLPositionActionDAO;
import by.epam.bookshop.entity.position_action.PositionAction;
import by.epam.bookshop.entity.position_action.PositionActionFactory;
import by.epam.bookshop.service.AbstractEntityService;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.validator.impl.PositionActionValidator;

import java.sql.Connection;

public class PositionActionService extends AbstractEntityService<PositionAction> {

    private final static PositionActionService INSTANCE = new PositionActionService();

    public static PositionActionService getInstance() {
        return INSTANCE;
    }

    public MySQLPositionActionDAO getDAO(Connection connection) {
        return new MySQLPositionActionDAO(connection);
    }

    public PositionActionFactory getFactory() {
        return new PositionActionFactory();
    }

    @Override
    public EntityValidator<PositionAction> getValidator() {
        return new PositionActionValidator();
    }
}
