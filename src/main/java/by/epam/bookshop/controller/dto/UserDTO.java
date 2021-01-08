package by.epam.bookshop.controller.dto;

import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.util.AddressObject;

import java.net.URL;

public class UserDTO extends User {

    private User user;

    public UserDTO (User user) {
        super(user.getFirstName(), user.getLastName(),
                user.getLogin(), 0, user.getAddress(),
                user.getPhotoLink(), user.getStatus());
        this.setId(user.getId());

    }

    private UserDTO(String firstName, String lastName, String login, int password,
                    AddressObject address, URL photoLink, UserStatus status) {
        super(firstName, lastName, login, password, address, photoLink, status);
    }
}
