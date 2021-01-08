package by.epam.bookshop.entity.book;

import by.epam.bookshop.entity.author.Author;
import by.epam.bookshop.entity.Entity;

import java.net.URL;
import java.util.Objects;

public class Book extends Entity {
    private int id;
    private String title;
    private Author author;
    private String description;
    private float price;
    private URL photoLink;

    public Book(String title, Author author, String description, float price, URL photoLink) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.photoLink = photoLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public URL getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(URL photoLink) {
        this.photoLink = photoLink;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id == book.id &&
                Float.compare(book.price, price) == 0 &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, description, price);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ",\n author=" + author.toString() +
                ", \ndescription='" + description + '\'' +
                '}';
    }
}
