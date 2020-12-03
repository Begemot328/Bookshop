package by.epam.bookshop.entity.shop;

import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.exceptions.FactoryException;

public class ShopFactory implements EntityFactory<Shop> {

    private static final String WRONG_INPUT_DATA = "Wrong input data";

    @Override
    public Shop create(Object... args) throws FactoryException {
        if (args.length < 2
                || !(args[0] instanceof String)
                || !(args[1] instanceof String)
                || !(args[2] instanceof String)
                || ((String) args[0]).isEmpty()
                || ((String) args[1]).isEmpty()
                || ((String) args[2]).isEmpty()) {
            throw new FactoryException(getWrongDataMessage());
        }
        return new Shop((String) args[0], (String) args[1], (String) args[2]);
    }
}
