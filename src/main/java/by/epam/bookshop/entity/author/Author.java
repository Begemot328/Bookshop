package by.epam.bookshop.entity.author;

import by.epam.bookshop.entity.Entity;

import java.net.URL;
import java.util.Objects;

public class Author extends Entity {
    private int id;
    private String firstName;
    private String lastName;
    private URL photoLink;

    public URL getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(URL photoLink) {
        this.photoLink = photoLink;
    }


    Author(String firstName, String lastName, URL photoLink) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoLink = photoLink;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return id == author.id &&
                Objects.equals(firstName, author.firstName) &&
                Objects.equals(lastName, author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
