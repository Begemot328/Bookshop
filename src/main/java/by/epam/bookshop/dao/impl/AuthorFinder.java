package by.epam.bookshop.dao.impl;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.author.Author;

import java.util.Collection;

public class AuthorFinder extends EntityFinder<Author> {
    private final static String SQL_QUERY = "SELECT * FROM ([QUERY])";
    private final static String VIEW = "[VIEW]";
    private final static String VIEW_NAME = "authors";
    private static final String WHERE = "WHERE [PARAMETER] = [VALUE]";
    private static final String PARAMETER = "[PARAMETER]";
    private static final String VALUE = "[VALUE]";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String ID = "ID";
    private static final CharSequence LASTNAME = "LASTNAME";

    public AuthorFinder() {
        super(VIEW_NAME);
    }

    public EntityFinder<Author> findByFirstName(String firstname) {
        return this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, FIRSTNAME)
                        .replace(VALUE, firstname));
    }

    public EntityFinder<Author> findByLastName(String lastname) {
        return this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, LASTNAME)
                        .replace(VALUE, lastname));
    }

    public EntityFinder<Author> findByID(int id) {
        return this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, ID)
                        .replace(VALUE, Integer.toString(id)));
    }
}
