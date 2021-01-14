package by.epam.bookshop.entity.shop;

import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.impl.ShopValidator;

import java.net.URL;

public class ShopFactory extends AbstractEntityFactory<Shop> {

    public ShopValidator getValidator() {
        return new ShopValidator();
    }

    @Override
    protected Shop createEntity(Object... args) {
        return new Shop((String) args[0], (AddressObject) args[1], (URL) args[2]);
    }
}
