package by.epam.bookshop.entity.position_action;

import by.epam.bookshop.entity.*;
import by.epam.bookshop.entity.position.Position;
import by.epam.bookshop.entity.position.PositionStatus;
import by.epam.bookshop.entity.shop.Shop;
import by.epam.bookshop.entity.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class PositionAction extends Entity implements Pricefull {
    private int id;
    private Position initialPosition;
    private Position finalPosition;
    private User buyer;
    private User seller;
    private LocalDateTime date;
    private int quantity;
    private PositionStatus initialStatus;
    private PositionStatus finalStatus;
    private Shop shop;
    private float currentPrice;

    public PositionAction(Position initialPosition, Position finalPosition,
                          User buyer, User seller,
                          LocalDateTime date, int quantity,
                          PositionStatus initialStatus, PositionStatus finalStatus,
                          Shop shop, Float currentPrice) {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
        this.buyer = buyer;
        this.seller = seller;
        this.date = date;
        this.quantity = quantity;
        this.initialStatus = initialStatus;
        this.finalStatus = finalStatus;
        this.shop = shop;
        this.currentPrice = currentPrice;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Position initialPosition) {
        this.initialPosition = initialPosition;
    }

    public Position getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(Position finalPosition) {
        this.finalPosition = finalPosition;
    }

    @Override
    public float getTotalPrice() {
        return currentPrice * quantity;
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

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
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
                ", \n initial position=" + initialPosition +
                ", \n final position=" + finalPosition +
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
        if (!(o instanceof PositionAction)) return false;
        PositionAction that = (PositionAction) o;
        return id == that.id &&
                quantity == that.quantity &&
                Objects.equals(initialPosition, that.initialPosition) &&
                Objects.equals(finalPosition, that.finalPosition) &&
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
        return Objects.hash(id, initialPosition, finalPosition, buyer, seller, date,
                quantity, initialStatus, finalStatus, shop, currentPrice);
    }
}
