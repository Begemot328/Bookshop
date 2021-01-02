package by.epam.bookshop.pool;

import by.epam.bookshop.resource.DbResourceManager;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


public class ConnectionPool implements Closeable {

    private static final String INTERRUPTED_EXCEPTION = "Interrupted exception: ";
    private static final String SQL_EXCEPTION = "SQL exception: ";
    private static final String USE_UNICODE = "useUnicode";
    private static final String TIMEZONE = "serverTimezone";
    private static final String EQUALS_MARK = "=";
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final int TIMEOUT = Integer.parseInt(
            DbResourceManager.getInstance().getValue(DbParameter.DB_TIMEOUT));
    private static final int CONNECTION_ATTEMPTIONS = Integer.parseInt(
            DbResourceManager.getInstance().getValue(DbParameter.DB_ATTEMPTIONS));
    private static final int DEFAULT_POOL_SIZE = Integer.parseInt(
            DbResourceManager.getInstance().getValue(DbParameter.DB_POOL_SIZE));
    private static final int MAX_POOL_SIZE = Integer.parseInt(
            DbResourceManager.getInstance().getValue(DbParameter.DB_POOL_SIZE));
    private static final String DB_URL =
            DbResourceManager.getInstance().getValue(DbParameter.DB_URL);
    private static final String DB_PASSWORD =
            DbResourceManager.getInstance().getValue(DbParameter.DB_PASSWORD);
    private static final String DB_USER =
            DbResourceManager.getInstance().getValue(DbParameter.DB_USER);
    private static final String DB_TIMEZONE =
            DbResourceManager.getInstance().getValue(DbParameter.DB_TIMEZONE);
    private static final String DB_ENCODING =
            DbResourceManager.getInstance().getValue(DbParameter.DB_ENCODING);
    private static final String DB_USE_UNICODE =
            DbResourceManager.getInstance().getValue(DbParameter.DB_USE_UNICODE);
    private static int capacity;


    private static void addDBparameter(String parameterName, String value) {
        if (parameterName != null && !parameterName.isEmpty()
                && value != null && !value.isEmpty()) {
            dbParameters.put(parameterName, value);
        }
    }

    private static Map<String, String> dbParameters = new HashMap<>();

    {
        addDBparameter(TIMEZONE, DB_TIMEZONE);
        addDBparameter(USE_UNICODE, DB_USE_UNICODE);
    }

    /**
     * The free connections.
     */
    private BlockingQueue<ConnectionProxy> freeConnections;

    /**
     * The working connections.
     */
    private BlockingQueue<ConnectionProxy> workingConnections;

    private static ConnectionPool INSTANCE;


    private ConnectionPool() {
    }

    private String getDbUrl() {
        String result = DB_URL;
        Set<String> keyset = dbParameters.keySet();

        if (keyset.size() == 0) {
            return result;
        }

        result = result.concat(QUESTION_MARK);

        for (String key : keyset) {
            result = result.concat(
                    key + EQUALS_MARK + dbParameters.get(key) + AMPERSAND);
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    private void init() throws SQLException {
        freeConnections = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);
        workingConnections = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);
        capacity = DEFAULT_POOL_SIZE;
        Stream.generate(() -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                ConnectionProxy connectionProxy
                        = new ConnectionProxy(DriverManager.getConnection(
                        //  "jdbc:mysql://localhost:3306/bookshop?useUnicode=true&serverTimezone=UTC",
                        getDbUrl(),
                        DB_USER, DB_PASSWORD));
                return connectionProxy;
            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new ConnectionPoolRuntimeException(SQL_EXCEPTION, e);
            }
        })
                .limit(10).forEach(e1 -> {
            try {
                freeConnections.put(e1);
            } catch (InterruptedException e) {
                throw new ConnectionPoolRuntimeException(SQL_EXCEPTION, e);
            }
        });
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPool();
            try {
                INSTANCE.init();
            } catch (SQLException e) {
                throw new ConnectionPoolException(SQL_EXCEPTION, e);
            }
        }
        return INSTANCE;
    }

    public Connection getConnection() throws ConnectionPoolException {
        ConnectionProxy connection;

        if (freeConnections.remainingCapacity() == 0 && capacity < MAX_POOL_SIZE) {
            freeConnections = new ArrayBlockingQueue<ConnectionProxy>(++capacity);
            BlockingQueue<ConnectionProxy> newWorkingConnections = new ArrayBlockingQueue<ConnectionProxy>(++capacity);
            newWorkingConnections.addAll(workingConnections);
            workingConnections = newWorkingConnections;
        }


        try {
            connection = freeConnections.poll(TIMEOUT, TimeUnit.SECONDS);
            workingConnections.add(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(INTERRUPTED_EXCEPTION, e);
        }
    }


    @Override
    public void close() throws IOException {
        workingConnections.clear();
        freeConnections.clear();
    }

    public void releaseConnection(ConnectionProxy connection) throws ConnectionPoolException {
        try {
            workingConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(INTERRUPTED_EXCEPTION, e);
        }
    }
}
