package by.epam.bookshop.entity.shop;

import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.AuthorValidator;
import by.epam.bookshop.validator.ShopValidator;

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
