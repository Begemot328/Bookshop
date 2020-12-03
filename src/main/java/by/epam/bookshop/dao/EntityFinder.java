package by.epam.bookshop.dao;

import by.epam.bookshop.entity.Entity;

import java.util.Collection;

public abstract class EntityFinder<T extends Entity> {
    final static String SQL_QUERY = "SELECT * FROM ([QUERY])";
    final static String SQL_VIEW = "SELECT * FROM ([QUERY])";
    public static final String QUERY = "[QUERY]";

    private String query;



    public EntityFinder(String view) {
        this.query = SQL_VIEW.replace(QUERY, view);
    }

    public EntityFinder<T> findBy(String query) {
        SQL_QUERY.replace(QUERY, query);
        return this;
    }

    public String getQuery() {
        return query;
    }

}
