package test.by.epam.bookshop.validate;

import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.validator.impl.AuthorValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class AuthorValidatorTest  extends Assert {
    EntityValidator<Author>  validator;
    Author author;
    URL url;


    public AuthorValidatorTest() throws FactoryException, MalformedURLException {
    }

    @Before
    public void init() throws MalformedURLException, FactoryException {
        url = new URL("http://www.google.com");
        validator = new AuthorValidator();
        author = new AuthorFactory().create("Alexander", "Pushkin", "description", url);

    }

    @Test
    public void validateAuthorTest() throws ValidationException {
        validator.validate(author);

        author.setDescription("");
        validator.validate(author);

        author.setDescription(null);
        validator.validate(author);

        author.setPhotoLink(null);
        validator.validate(author);
    }

    @Test (expected = ValidationException.class)
    public void validateAuthor1Test() throws ValidationException {
        author.setLastName("");
        validator.validate(author);
    }

    @Test (expected = ValidationException.class)
    public void validateAuthor2Test() throws ValidationException {
        author.setFirstName("");
        validator.validate(author);
    }

    @Test (expected = ValidationException.class)
    public void validateAuthor3Test() throws ValidationException {
        author.setLastName(null);
        validator.validate(author);
    }

    @Test (expected = ValidationException.class)
    public void validateAuthor4Test() throws ValidationException {
        author.setFirstName(null);
        validator.validate(author);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs1Test() throws ValidationException {
        validator.validate("Alexander", 1, null, null);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs2Test() throws ValidationException {
        validator.validate("Alexander", "Pushkin", 1,  null);
    }
    @Test (expected = ValidationException.class)
    public void validateArgs3Test() throws ValidationException {
        validator.validate("Alexander", "Pushkin", null, 2);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs4Test() throws ValidationException {
        validator.validate("Alexander", null, null);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs5Test() throws ValidationException {
        validator.validate("Alexander", "Pushkin");
    }

    @Test (expected = ValidationException.class)
    public void validateArgs6Test() throws ValidationException {
        validator.validate(Integer.valueOf(1), "Alexander", "Pushkin", null);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs7Test() throws ValidationException {
        validator.validate("Alexander", "Pushkin", null);
    }

    @Test
    public void validateArgsTest() throws ValidationException, MalformedURLException {
        validator.validate("Alexander", "Pushkin", null, null);
        validator.validate("Alexander", "Pushkin", "null", null);
        validator.validate("Alexander", "Pushkin", "", null);
        validator.validate("Alexander", "Pushkin", "null", new URL("http://www.google.com"));
        validator.validate("Alexander", "Pushkin", null, new URL("http://www.google.com"));
    }
}
