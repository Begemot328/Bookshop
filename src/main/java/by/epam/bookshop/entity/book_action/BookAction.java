package by.epam.bookshop.entity.book_action;

import by.epam.bookshop.entity.*;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class BookAction extends Entity implements Pricefull {
    private int id;
    private Book book;
    private User buyer;
    private User seller;
    private LocalDateTime date;
    private int quantity;
    private PositionStatus initialStatus;
    private PositionStatus finalStatus;
    private Shop shop;
    private float currentPrice;

    public BookAction(Book book, User buyer, User seller,
                      LocalDateTime date, int quantity,
                      PositionStatus initialStatus, PositionStatus finalStatus,
                      Shop shop, Float currentPrice) {
        this.book = book;
        this.buyer = buyer;
        this.seller = seller;
        this.date = date;
        this.quantity = quantity;
        this.initialStatus = initialStatus;
        this.finalStatus = finalStatus;
        this.shop = shop;
        this.currentPrice = currentPrice;
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

    public User getBuyer() {
        return buyer;
    }

    public User getSeller() {
        return seller;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

    public PositionStatus getInitialStatus() {
        return initialStatus;
    }

    public PositionStatus getFinalStatus() {
        return finalStatus;
    }

    public Shop getShop() {
        return shop;
    }

    public Book getBook() {
        return book;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setInitialStatus(PositionStatus initialStatus) {
        this.initialStatus = initialStatus;
    }

    public void setFinalStatus(PositionStatus finalStatus) {
        this.finalStatus = finalStatus;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "BookAction{" +
                "id=" + id +
                ", \n book=" + book +
                ", \n buyer=" + buyer +
                ", \n seller=" + seller +
                ", date=" + date +
                ", quantity=" + quantity +
                ", initialStatus=" + initialStatus +
                ", finalStatus=" + finalStatus +
                ", \n shop=" + shop +
                ", \n current price =" + currentPrice +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookAction)) return false;
        BookAction that = (BookAction) o;
        return id == that.id &&
                quantity == that.quantity &&
                Objects.equals(book, that.book) &&
                Objects.equals(buyer, that.buyer) &&
                Objects.equals(seller, that.seller) &&
                Objects.equals(date, that.date) &&
                initialStatus == that.initialStatus &&
                finalStatus == that.finalStatus &&
                Objects.equals(shop, that.shop)
                && currentPrice == that.currentPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, buyer, seller, date,
                quantity, initialStatus, finalStatus, shop, currentPrice);
    }
}
