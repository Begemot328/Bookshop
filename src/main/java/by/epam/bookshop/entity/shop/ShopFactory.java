package by.epam.bookshop.entity.shop;

import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.exceptions.FactoryException;

public class ShopFactory extends AbstractEntityFactory<Shop> {

    @Override
    public Shop create(Object... args) throws FactoryException {
        if (args.length < 3
                || !(args[0] instanceof String)
                || !(args[1] instanceof String)
                || ((String) args[0]).isEmpty()
                || ((String) args[1]).isEmpty()) {
            throw new FactoryException(getWrongDataMessage());
        }
        return new Shop((String) args[0], (String) args[1], (String) args[2]);
    }
}
