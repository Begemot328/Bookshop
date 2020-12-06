package by.epam.bookshop.dao.impl.book;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;


public class BookFinder extends EntityFinder<Book> {


    private final static String VIEW_NAME = "BOOKS";
    private static final String TITLE = "TITLE";
    private static final String AUTHOR_ID = "AUTHOR_ID";
    private static final String PRICE = "PRICE";
    private static final String ID = "ID";

    public BookFinder() {
        super(VIEW_NAME);
    }

    public BookFinder findByTitle(String title) {
        return (BookFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, TITLE)
                        .replace(VALUE, title));
    }

    public BookFinder findByAuthor(Integer authorId) {
        return (BookFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, AUTHOR_ID)
                        .replace(VALUE, Integer.toString(authorId)));
    }

    public BookFinder findByPriceMore(Float price) {
        return (BookFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, PRICE).replace(COMPARE, MORE)
                        .replace(VALUE, Float.toString(price)));
    }

    public BookFinder findByPriceLess(Float price) {
        return (BookFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, PRICE).replace(COMPARE, LESS)
                        .replace(VALUE, Float.toString(price)));
    }

    public BookFinder findByPrice(Float price) {
        return (BookFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, PRICE)
                        .replace(VALUE, Float.toString(price)));
    }

    public BookFinder findByID(int id) {
        BookFinder finder  = (BookFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, ID)
                        .replace(VALUE, Integer.toString(id)));
        return finder;
    }
}
