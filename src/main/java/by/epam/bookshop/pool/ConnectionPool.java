package by.epam.bookshop.pool;

import by.epam.bookshop.resource.DbResourceManager;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class ConnectionPool implements Closeable {

    private static final String INTERRUPTED_EXCEPTION = "Interrupted exception: ";
    private static final String SQL_EXCEPTION = "SQL exception: ";
    private static final int TIMEOUT = Integer.parseInt(
            DbResourceManager.getInstance().getValue(DbParameter.DB_TIMEOUT));
    private static final int CONNECTION_ATTEMPTIONS = Integer.parseInt(
            DbResourceManager.getInstance().getValue(DbParameter.DB_ATTEMPTIONS));
    private static final int DEFAULT_POOL_SIZE = Integer.parseInt(
            DbResourceManager.getInstance().getValue(DbParameter.DB_POOL_SIZE));

    /** The free connections. */
    private BlockingQueue<ConnectionProxy> freeConnections;

    /** The working connections. */
    private BlockingQueue<ConnectionProxy> workingConnections;

    private static ConnectionPool INSTANCE;


    private ConnectionPool() {
    }

    private void init() throws SQLException {
        freeConnections = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);
        workingConnections = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);

        Stream.generate(() -> {
            try {
                ConnectionProxy connectionProxy
                 = new ConnectionProxy(DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookshop?useUnicode=true&serverTimezone=UTC",
                    "root", "1234567890"));
                 return connectionProxy;
            } catch (SQLException e) {
                throw new ConnectionPoolRuntimeException(SQL_EXCEPTION, e);
            }})
            .limit(10).forEach(e1 -> {
                    try {
                        freeConnections.put(e1);
                    } catch (InterruptedException e) {
                        throw new ConnectionPoolRuntimeException(SQL_EXCEPTION, e);
                    }
                });


     /*   for (ConnectionProxy connection: freeConnections) {
            connection = new ConnectionProxy(DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookshop?useUnicode=true&serverTimezone=UTC",
                    "root", "1234567890"));
*/
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
