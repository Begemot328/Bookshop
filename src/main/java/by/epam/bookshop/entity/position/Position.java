package by.epam.bookshop.entity.position;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.entity.Pricefull;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.book.Book;

import java.util.Objects;

public class Position extends Entity implements Pricefull {
    private int id;
    private Book book;
    private Shop shop;
    private PositionStatus status;
    private String note;
    private int quantity;

    public Position(Book book, Shop shop,
                    PositionStatus status,
                    String note, int quantity) {
        this.book = book;
        this.shop = shop;
        this.status = status;
        this.note = note;
        this.quantity = quantity;
    }

    @Override
    public float getTotalPrice() {
        return book.getPrice() * quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setStatus(PositionStatus status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Book getBook() {
        return book;
    }

    public Shop getShop() {
        return shop;
    }

    public PositionStatus getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", \n book=" + book +
                ", \n shop=" + shop +
                ", \n status=" + status +
                ", \n note='" + note + '\'' +
                ", \n quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return id == position.id &&
                quantity == position.quantity &&
                Objects.equals(book, position.book) &&
                Objects.equals(shop, position.shop) &&
                status == position.status &&
                Objects.equals(note, position.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, shop, status, note, quantity);
    }
}
