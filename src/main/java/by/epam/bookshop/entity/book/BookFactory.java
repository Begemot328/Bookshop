package by.epam.bookshop.entity.book;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.exceptions.FactoryException;
import by.epam.bookshop.validator.AuthorValidator;
import by.epam.bookshop.validator.BookValidator;

public class BookFactory extends AbstractEntityFactory<Book> {

    public BookValidator getValidator() {
        return new BookValidator();
    }

    @Override
    protected Book createEntity(Object... args) {

        return new Book((String) args[0],
                (Author) args[1],
                (String) args[2],
                Float.parseFloat(args[3].toString()),
                (String) args[4]);
    }
}
