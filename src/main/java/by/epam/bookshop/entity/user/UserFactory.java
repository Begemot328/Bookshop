package by.epam.bookshop.entity.user;

import by.epam.bookshop.entity.EntityFactory;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.exceptions.FactoryException;

public class UserFactory implements EntityFactory<User> {

    /*
        private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String adress;
    private String photoLink;
    private UserStatus status;
     */
    @Override
    public User create(Object... args) throws FactoryException {
        if (args.length < 7
                || !(args[0] instanceof String)
                || ((String) args[0]).isEmpty()
                || !(args[1] instanceof String)
                || ((String) args[1]).isEmpty()
                || !(args[2] instanceof String)
                || ((String) args[2]).isEmpty()
                || !(args[3] instanceof Integer)//May be incorrect! (Integer.class.isInstance(args[2])
                || !(args[4] instanceof String)
                || ((String) args[4]).isEmpty()
                || !(args[5] instanceof String)
                || ((String) args[5]).isEmpty()
                || !(args[6] instanceof UserStatus)) {
            throw new FactoryException(getWrongDataMessage());
        }
        return new User((String) args[0],
                (String) args[1],
                (String) args[2],
                (Integer) args[3],
                (String) args[4],
                (String) args[5],
                (UserStatus) args[6]);
    }
}
