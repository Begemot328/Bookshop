package test.by.epam.bookshop.validate;

import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.book.BookFactory;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.exceptions.ValidationException;
import by.epam.bookshop.validator.EntityValidator;
import by.epam.bookshop.validator.impl.AuthorValidator;
import by.epam.bookshop.validator.impl.BookValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class BookValidatorTest extends Assert {
    EntityValidator<Book>  validator;
    URL url;
    Author author;
    Book book;

    public BookValidatorTest() throws FactoryException, MalformedURLException {
    }

    @Before
    public void init() throws MalformedURLException, FactoryException {
        url = new URL("http://www.google.com");
        validator =  new BookValidator();
        author = new AuthorFactory().create("Alexander", "Pushkin", "description", url);
        book = new BookFactory().create("The tale about the Golden Rooster",
                author, "The tale about the Golden Rooster by Alexander Pushkin", (float) 10.1, url);

    }

    @Test
    public void validateBookTest() throws ValidationException {
        validator.validate(book);

        book.setDescription("");
        validator.validate(book);

        book.setDescription(null);
        validator.validate(book);

        book.setPhotoLink(null);
        validator.validate(book);

        book.setPhotoLink(null);
        validator.validate(book);
    }

    @Test (expected = ValidationException.class)
    public void validateBook1Test() throws ValidationException {
        book.setTitle("");
        validator.validate(book);
    }

    @Test (expected = ValidationException.class)
    public void validateAuthor2Test() throws ValidationException {
        book.setTitle(null);
        validator.validate(book);
    }

    @Test (expected = ValidationException.class)
    public void validateAuthor3Test() throws ValidationException {
        book.setAuthor(null);
        validator.validate(book);
    }

    @Test (expected = ValidationException.class)
    public void validateAuthor4Test() throws ValidationException {
        book.setPrice(-1);
        validator.validate(book);
    }

    @Test (expected = ValidationException.class)
    public void validateAuthor5Test() throws ValidationException {
        book.setAuthor(null);
        validator.validate(book);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs1Test() throws ValidationException {
        validator.validate(author, "The tale about the Golden Rooster by Alexander Pushkin",
                (float) 10.1, url);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs2Test() throws ValidationException {
        validator.validate("The tale about the Golden Rooster",
                author, "The tale about the Golden Rooster by Alexander Pushkin", (float) 10.1);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs3Test() throws ValidationException {
        validator.validate(1,
                author, "The tale about the Golden Rooster by Alexander Pushkin", (float) 10.1, url);
    }

    @Test (expected = ValidationException.class)
    public void validateArgs4Test() throws ValidationException {
        validator.validate("The tale about the Golden Rooster",
                author, "The tale about the Golden Rooster by Alexander Pushkin", url, (float) 10.1);
    }

    @Test
    public void validateArgsTest() throws ValidationException, MalformedURLException {
        validator.validate("The tale about the Golden Rooster",
                author, "The tale about the Golden Rooster by Alexander Pushkin", (float) 10.1, url);
        validator.validate("The tale about the Golden Rooster",
                author, "The tale about the Golden Rooster by Alexander Pushkin", (float) 10.1, null);
        validator.validate("The tale about the Golden Rooster",
                author, "", (float) 10.1, url);
        validator.validate("The tale about the Golden Rooster",
                author, null, (float) 10.1, url);

    }
}
