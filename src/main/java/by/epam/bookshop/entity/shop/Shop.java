package by.epam.bookshop.entity.shop;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.util.AddressObject;

import java.net.URL;
import java.util.Objects;

public class Shop extends Entity {
    private int id;
    private String name;
    private AddressObject address;
    private URL photoLink;

    public Shop(String name, AddressObject address, URL photoLink) {
        this.name = name;
        this.address = address;
        this.photoLink = photoLink;
    }

    public String getName() {
        return name;
    }

    public AddressObject getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(AddressObject address) {
        this.address = address;
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop)) return false;
        Shop shop = (Shop) o;
        return id == shop.id &&
                Objects.equals(name, shop.name) &&
                Objects.equals(address, shop.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    public URL getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(URL photoLink) {
        this.photoLink = photoLink;
    }
}
