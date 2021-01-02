package by.epam.bookshop.service.user;

import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.service.EntityValidator;

public class UserValidator implements EntityValidator<User> {

    public boolean validate(Object ... args) {
        boolean result = true;
        if (args.length < 7
                || !(args[0] instanceof String)
                || ((String) args[0]).isEmpty()
                || !(args[1] instanceof String)
                || ((String) args[1]).isEmpty()
                || !(args[2] instanceof String)
                || ((String) args[2]).isEmpty()
                || !(args[3] instanceof Integer)
                || (Integer.parseInt((String) args[3]) <= 0)
                || !(args[4] instanceof String)
                || ((String) args[4]).isEmpty()
                || !(args[5] == null|| (args[5] instanceof String  && ((String) args[5]).isEmpty()))
                || !(args[6] instanceof UserStatus)) {
            result = false;
        }
        return result;
    }
}
