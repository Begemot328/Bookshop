package by.epam.bookshop.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class BookAction extends Entity implements Pricefull{
    private int id;
    private Book book;
    private User buyer;
    private User seller;
    private LocalDateTime date;
    private int quantity;
    private PositionStatus initialStatus;
    private PositionStatus finalStatus;
    private Shop shop;

    public BookAction(Book book, User buyer, User seller,
                      LocalDateTime date, int quantity,
                      PositionStatus initialStatus, PositionStatus finalStatus,
                      Shop shop) {
        this.book = book;
        this.buyer = buyer;
        this.seller = seller;
        this.date = date;
        this.quantity = quantity;
        this.initialStatus = initialStatus;
        this.finalStatus = finalStatus;
        this.shop = shop;
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
                Objects.equals(shop, that.shop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, buyer, seller, date,
                quantity, initialStatus, finalStatus, shop);
    }
}
