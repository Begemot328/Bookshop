package by.epam.bookshop.dao;

import by.epam.bookshop.entity.Entity;

public class EntityFinder<T extends Entity> {
    protected final static String SELECT_FROM_RESULT = "SELECT * FROM ([QUERY]) AS [ALIAS]";
    protected final static String SQL_QUERY = "SELECT * FROM [QUERY]";
    protected static final String WHERE = " WHERE [PARAMETER] = '[VALUE]'";
    protected static final String OR = " OR [PARAMETER] = '[VALUE]'";
    protected static final String WHERE_COMPARING = " WHERE [PARAMETER] [COMPARE] '[VALUE]'";
    protected static final String COMPARE = "[COMPARE]";
    protected static final String MORE = ">";
    protected static final String LESS = "<";
    protected static final String EQUAL = "=";
    protected static final String IN = "IN";
    protected static final String ALL = "*";
    protected static final String WHERE_LIKE = " WHERE [PARAMETER] LIKE '%[VALUE]%'";
    protected static final String PARAMETER = "[PARAMETER]";
    protected static final String VALUE = "[VALUE]";
    protected final static String RESULT = "([QUERY]) AS [ALIAS]";
    protected final static String SELECT_FROM_TABLE = "SELECT * FROM [QUERY]";
    protected static final String ID = "ID";
    protected static final String QUERY = "[QUERY]";
    protected static final String ALIAS = "[ALIAS]";
    protected static final String ALIAS_NAME = "ALIAS";
    protected static final String INTERSECT = " INTERSECT ";

    protected String query;
    protected int counter = 0;

    public EntityFinder(String table) {
        this.query = SELECT_FROM_TABLE.replace(QUERY, table);
    }

    public EntityFinder<T> findBy(String query) {
        this.query = query.replace(QUERY, RESULT)
                .replace(QUERY, this.query)
                .replace(ALIAS, ALIAS_NAME + ++counter);
        return this;
    }

    public EntityFinder<T> findByID(int id) {
        EntityFinder<T> finder  = this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, ID)
                        .replace(VALUE, Integer.toString(id)));
        return finder;
    }

    public String getQuery() {
        return query;
    }

}
