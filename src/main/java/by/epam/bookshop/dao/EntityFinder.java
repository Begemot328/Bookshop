package by.epam.bookshop.dao;

import by.epam.bookshop.entity.Entity;

public abstract class EntityFinder<T extends Entity> {
    protected final static String SELECT_FROM_RESULT = "SELECT * FROM ([QUERY]) AS [ALIAS]";

    protected final static String RESULT = "([QUERY]) AS [ALIAS]";
    protected final static String SELECT_FROM_TABLE = "SELECT * FROM [QUERY]";
    public static final String QUERY = "[QUERY]";
    public static final String ALIAS = "[ALIAS]";
    public static final String ALIAS_NAME = "ALIAS";


    private String query;
    private int counter = 0;

    public EntityFinder(String table) {
        this.query = SELECT_FROM_TABLE.replace(QUERY, table);
    }

    public EntityFinder<T> findBy(String query) {
        this.query = query.replace(QUERY, RESULT)
                .replace(QUERY, this.query)
                .replace(QUERY, this.query)
                .replace(ALIAS, ALIAS_NAME + ++counter);
        return this;
    }

    public String getQuery() {
        return query;
    }

}
