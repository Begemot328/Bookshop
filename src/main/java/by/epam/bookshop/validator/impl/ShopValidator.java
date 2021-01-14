package by.epam.bookshop.validator.impl;

import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.AbstractEntityValidator;

import java.net.URL;

public class ShopValidator extends AbstractEntityValidator<Shop> {

    private static final String INPUT_ERROR = "error.input";
    private static final Class[] types = {
            String.class,
            AddressObject.class,
            URL.class
    };

    protected Class[] getTypes() {
        return types;
    }

    @Override
    public void validate(Shop shop) throws ValidationException {
        if (shop.getAddress() == null
                || shop.getName() == null
                || !shop.getAddress().isStatus()
                || shop.getName().isEmpty())
        {
            throw new ValidationException(INPUT_ERROR);
        }
    }
}
