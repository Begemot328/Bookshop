package test.by.epam.bookshop.validate;

import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.shop.ShopFactory;
import by.epam.bookshop.exceptions.AddressException;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.util.AddressObject;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.validator.impl.ShopValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ShopValidatorTest extends Assert {
    EntityValidator<Shop>  validator;
    Shop shop;
    AddressObject address;
    URL url;


    public ShopValidatorTest() throws FactoryException, MalformedURLException {
    }

    @Before
    public void init() throws MalformedURLException, FactoryException, AddressException {
        url = new URL("http://www.google.com");
        validator = new ShopValidator();
        address = new AddressObject("Грушевская, 73, Минск");
        shop = new ShopFactory().create("На Сурганова", address, new URL("http://www.google.com"));

    }

    @Test
    public void validateShopTest() throws ValidationException {
        validator.validate(shop);

        shop.setPhotoLink(null);
        validator.validate(shop);
    }

    @Test (expected = ValidationException.class)
    public void validateShop1Test() throws ValidationException {
        shop.setName("");
        validator.validate(shop);
    }

    @Test (expected = ValidationException.class)
    public void validateShop2Test() throws ValidationException {
        shop.setName(null);
        validator.validate(shop);
    }

    @Test (expected = ValidationException.class)
    public void validateShop3Test() throws ValidationException {
        shop.setAddress(null);
        validator.validate(shop);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs1Test() throws ValidationException, MalformedURLException {
        validator.validate("На Сурганова", new URL("http://www.google.com"));
    }

    @Test (expected = ValidationException.class)
    public void validateArgs4Test() throws ValidationException {
        validator.validate("На Сурганова", address);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs6Test() throws ValidationException, MalformedURLException {
        validator.validate("На Сурганова", new URL("http://www.google.com"), address);
    }

    @Test
    public void validateArgsTest() throws ValidationException, MalformedURLException {
        validator.validate("На Сурганова", address, new URL("http://www.google.com"));
        validator.validate("На Сурганова", address, null);
    }
}
