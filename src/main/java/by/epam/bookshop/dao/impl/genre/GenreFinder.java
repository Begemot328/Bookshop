package by.epam.bookshop.dao.impl.genre;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.entity.genre.Genre;

public class GenreFinder extends EntityFinder<Genre> {
    private final static String VIEW_NAME = "GENRES";
    private final static String SPECIAL_VIEW_NAME = "BOOKS_GENRES";
    private static final String ID = "ID";
    private static final String BOOK_ID = "BOOK_ID";
    private static final String GENRE_ID = "GENRE_ID";

    public GenreFinder() {
        super(VIEW_NAME);
    }

    private GenreFinder(String view) {
        super(view);
    }

    public GenreFinder findByBook(int id) {

        String interimQuery = SQL_QUERY.replace(ALL, GENRE_ID)
                .replace(QUERY, SPECIAL_VIEW_NAME)
                + WHERE.replace(PARAMETER, ID)
                .replace(VALUE, Integer.toString(id));

        return (GenreFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, ID)
                        .replace(COMPARE, IN)
                        .replace(VALUE, "(" + interimQuery + ")")
                        .replace("'", ""));
    }
}
