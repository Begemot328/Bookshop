package by.epam.bookshop.entity;

import java.util.Objects;

public class Shop extends Entity{
    private int id;
    private String name;
    private String adress;
    private String position;

    public Shop(String name, String adress, String position) {
        this.name = name;
        this.adress = adress;
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
                ", adress='" + adress + '\'' +
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
                Objects.equals(adress, shop.adress) &&
                Objects.equals(position, shop.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, adress, position);
    }
}
