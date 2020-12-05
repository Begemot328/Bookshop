package by.epam.bookshop.dao.impl.user;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;


public class UserFinder extends EntityFinder<User> {


    private final static String VIEW_NAME = "USERS";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWORD";
    private static final String ADRESS = "ADRESS";
    private static final String PHOTO_LINK = "PHOTO_LINK";
    private static final String STATUS = "STATUS";


    private static final String ID = "ID";

    public UserFinder() {
        super(VIEW_NAME);
    }

    public UserFinder findByFirstName(String firstName) {
        return (UserFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, FIRST_NAME)
                        .replace(VALUE, firstName));
    }

    public UserFinder findByLastName(String lastName) {
        return (UserFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, LAST_NAME)
                        .replace(VALUE, lastName));
    }

    public UserFinder findByAdress(String adress) {
        return (UserFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, ADRESS)
                        .replace(VALUE, adress));
    }

    public UserFinder findByStatus(UserStatus status) {
        UserFinder finder  = (UserFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, STATUS)
                        .replace(VALUE, Integer.toString(status.getId())));
        return finder;
    }

    public UserFinder findByID(int id) {
        UserFinder finder  = (UserFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, ID)
                        .replace(VALUE, Integer.toString(id)));
        return finder;
    }
}
