package test.by.epam.bookshop.service;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.author.AuthorFinder;
import by.epam.bookshop.dao.impl.author.MySQLAuthorDAO;
import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.author.AuthorFactory;
import by.epam.bookshop.exceptions.*;
import by.epam.bookshop.service.EntityService;
import by.epam.bookshop.service.author.AuthorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AuthorServiceTest extends Assert {
    MySQLAuthorDAO dao;
    MySQLAuthorDAO daoMock;
    Author author;
    AuthorService service;
    URL url;
    AuthorService serviceSpy;

    @Before
    public void init() throws DAOException, MalformedURLException, FactoryException, ServiceException {
        url = new URL("http://www.google.com");
        author = new AuthorFactory().create("Alexander", "Pushkin", "description", url);
        daoMock = Mockito.mock(MySQLAuthorDAO.class);
        dao = new MySQLAuthorDAO(null);
        Mockito.when(daoMock.create(Mockito.any(Author.class))).then(invocationOnMock -> {
            Author newAuthor = invocationOnMock.getArgumentAt(0, Author.class);
            newAuthor.setId(author.getId() + 1);
            author.setId(author.getId() + 1);
            return true;
        });
        Mockito.when(daoMock.read(Mockito.any(Integer.class))).thenReturn(author);
        Mockito.doNothing().when(daoMock).delete(Mockito.any(Integer.class));
        Mockito.doNothing().when(daoMock).update(Mockito.any(Author.class));
        Mockito.when(daoMock.countAll()).thenReturn(2);
        Mockito.when(daoMock.findAll()).thenReturn(Collections.singletonList(author));
        Mockito.when(daoMock.findBy(Mockito.any(AuthorFinder.class))).thenReturn(Collections.singletonList(author));
        Mockito.when(daoMock.countBy(Mockito.any(AuthorFinder.class))).thenReturn(1);

        service = (AuthorService) AuthorService.getInstance();
        serviceSpy = (AuthorService) Mockito.spy(service);
        Mockito.doReturn(daoMock).when(serviceSpy).getDAO(Mockito.any());
        Mockito.doReturn(null).when(serviceSpy).getConnection();
    }

    @Test
    public void createAuthorTest() throws ValidationException, ServiceException, DAOException {
        Author newAuthor = serviceSpy.create("Alexander", "Pushkin", "description", url);
        Mockito.verify(daoMock, Mockito.times(1)).create(author);
        assertEquals(author, newAuthor);
    }

    @Test
    public void deleteAuthorTest() throws ValidationException, ServiceException, DAOException {
        serviceSpy.delete(author);
        Mockito.verify(daoMock, Mockito.times(1)).delete(author.getId());
    }

    @Test
    public void readAuthorTest() throws ValidationException, ServiceException, DAOException {
        author.setId(1);
        Author newAuthor = serviceSpy.read(author.getId());
        Mockito.verify(daoMock, Mockito.times(1)).read(author.getId());
        assertEquals(author, newAuthor);
    }

    @Test
    public void findAllAuthorTest() throws ValidationException, ServiceException, DAOException {
        Collection<Author> list = serviceSpy.findAll();
                Mockito.verify(daoMock, Mockito.times(1)).findAll();
        assertEquals(list, Collections.singletonList(author));
    }

    @Test
    public void findByAuthorTest() throws ValidationException, ServiceException, DAOException {
        AuthorFinder finder = new AuthorFinder();
        Collection<Author> list = serviceSpy.findBy(finder);
                Mockito.verify(daoMock, Mockito.times(1))
                .findBy(finder);
        assertEquals(list, Collections.singletonList(author));
    }

    @Test
    public void countAllAuthorTest() throws ValidationException, ServiceException, DAOException {
        int count = serviceSpy.countAll();
                Mockito.verify(daoMock, Mockito.times(1)).countAll();
        assertEquals(count, 2);
    }

    @Test
    public void countByAuthorTest() throws ValidationException, ServiceException, DAOException {
        AuthorFinder finder = new AuthorFinder();
        int count = serviceSpy.countBy(finder);
        Mockito.verify(daoMock, Mockito.times(1))
                .countBy(finder);
        assertEquals(count, 1);
    }
}
