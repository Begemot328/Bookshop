package by.epam.bookshop.controller.dto;

import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.util.AddressObject;

import java.net.URL;

/**
 * User DTO class
 *
 * @author Yury Zmushko
 * @version 1.0
 */
public class UserDTO extends User {

    private User user;

    public UserDTO (User user) {
        super(user.getFirstName(), user.getLastName(),
                user.getLogin(), null, user.getAddress(),
                user.getPhotoLink(), user.getStatus());
        this.setId(user.getId());

    }

    private UserDTO(String firstName, String lastName, String login, int password,
                    AddressObject address, URL photoLink, UserStatus status) {
        super(firstName, lastName, login, null, address, photoLink, status);
    }
}
