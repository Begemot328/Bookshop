package by.epam.bookshop.dao.impl.book;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;

import java.util.ArrayList;
import java.util.Collection;


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

    public BookFinder findByAuthors(Collection<Author> authors) {
        String authorsQuery = new String();

        if(authors.isEmpty()) {
            return (BookFinder) (new BookFinder().findByID(-1));
        }
                boolean first = true;

        for (Author author:authors) {
            if (first) {
                first = true;
                authorsQuery = WHERE.replace(PARAMETER, AUTHOR_ID).replace(VALUE, Integer.toString(author.getId()));
            } else {
                authorsQuery = authorsQuery.concat(
                        OR.replace(PARAMETER, AUTHOR_ID)
                                .replace(VALUE, Integer.toString(author.getId())));
            }
        }
        return (BookFinder) this.findBy(SQL_QUERY + authorsQuery);
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
}
