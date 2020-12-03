package by.epam.bookshop.dao.impl;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.author.Author;


public class AuthorFinder extends EntityFinder<Author> {
    private final static String SQL_QUERY = "SELECT * FROM [QUERY]";
    private final static String VIEW_NAME = "authors";
    private static final String WHERE = " WHERE [PARAMETER] = '[VALUE]'";
    private static final String WHERE_LIKE = " WHERE [PARAMETER] LIKE '%[VALUE]%'";
    private static final String PARAMETER = "[PARAMETER]";
    private static final String VALUE = "[VALUE]";
    private static final String FIRSTNAME = "FIRST_NAME";
    private static final String ID = "ID";
    private static final String LASTNAME = "LAST_NAME";

    public AuthorFinder() {
        super(VIEW_NAME);
    }

    public AuthorFinder findByFirstName(String firstname) {
        return (AuthorFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, FIRSTNAME)
                        .replace(VALUE, firstname));
    }

    public AuthorFinder findByLastName(String lastname) {
        return (AuthorFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, LASTNAME)
                        .replace(VALUE, lastname));
    }

    public AuthorFinder findByID(int id) {

        AuthorFinder finder  = (AuthorFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, ID)
                        .replace(VALUE, Integer.toString(id)));
        return finder;
    }
}
