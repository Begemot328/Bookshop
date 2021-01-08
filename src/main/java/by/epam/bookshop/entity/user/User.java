package by.epam.bookshop.entity.user;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.util.AddressObject;

import java.net.URL;

public class User extends Entity {
    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private int password;
    private AddressObject address;
    private URL photoLink;
    private UserStatus status;

    public User(String firstName, String lastName,
                String login, int password,
                AddressObject address, URL photoLink,
                UserStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.photoLink = photoLink;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public AddressObject getAddress() {
        return address;
    }

    public void setAddress(AddressObject address) {
        this.address = address;
    }

    public URL getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(URL photoLink) {
        this.photoLink = photoLink;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
