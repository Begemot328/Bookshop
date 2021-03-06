package by.epam.bookshop.entity.book;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.user.AbstractEntityFactory;
import by.epam.bookshop.validator.impl.BookValidator;

import java.net.URL;

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
                (URL) args[4]);
    }
}
