package by.epam.bookshop.controller.dto;

import by.epam.bookshop.entity.user.User;
import by.epam.bookshop.entity.user.UserStatus;

public class UserDTO extends User {

    private User user;

    public UserDTO (User user) {
        super(user.getFirstName(), user.getLastName(),
                user.getLogin(), 0, user.getAdress(),
                user.getPhotoLink(), user.getStatus());

    }

    private UserDTO(String firstName, String lastName, String login, int password, String adress, String photoLink, UserStatus status) {
        super(firstName, lastName, login, password, adress, photoLink, status);
    }
}
