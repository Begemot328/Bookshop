package by.epam.bookshop.dao.impl.shop;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.shop.Shop;


public class ShopFinder extends EntityFinder<Shop> {


    private final static String VIEW_NAME = "SHOPS";
    private static final String NAME = "NAME";
    private static final String ADDRESS = "ADDRESS";
    private static final String POSITION = "POSITION";
    private static final String ID = "ID";

    public ShopFinder() {
        super(VIEW_NAME);
    }

    public ShopFinder findByName(String name) {
        return (ShopFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, NAME)
                        .replace(VALUE, name));
    }

    public ShopFinder findByAdress(String adress) {
        return (ShopFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, ADDRESS)
                        .replace(VALUE, adress));
    }
}
