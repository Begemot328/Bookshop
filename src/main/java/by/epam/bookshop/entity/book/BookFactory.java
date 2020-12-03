package by.epam.bookshop.entity.book;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.exceptions.FactoryException;

public class BookFactory implements EntityFactory<Book> {

    @Override
    public Book create(Object... args) throws FactoryException {
        if (args.length < 3
                || !(args[0] instanceof String)
                || !(args[1] instanceof Author)
                || !(args[2] instanceof String)
                || !(args[3] instanceof Float)
                || ((String) args[0]).isEmpty()
                || ((String) args[2]).isEmpty()) {
            throw new FactoryException(getWrongDataMessage());
        }
        return new Book((String) args[0],
                (Author) args[1],
                (String) args[2],
                (Float) args[3]);
    }


}
