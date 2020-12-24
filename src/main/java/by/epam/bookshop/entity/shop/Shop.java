package by.epam.bookshop.entity.shop;

import by.epam.bookshop.entity.Entity;

import java.util.Objects;

public class Shop extends Entity {
    private int id;
    private String name;
    private String address;
    private String position;
    private String photoLink;

    public Shop(String name, String address, String position) {
        this.name = name;
        this.address = address;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + address + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop)) return false;
        Shop shop = (Shop) o;
        return id == shop.id &&
                Objects.equals(name, shop.name) &&
                Objects.equals(address, shop.address) &&
                Objects.equals(position, shop.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, position);
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }
}
