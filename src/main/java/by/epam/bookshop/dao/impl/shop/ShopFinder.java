package by.epam.bookshop.dao.impl.shop;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.shop.Shop;


public class ShopFinder extends EntityFinder<Shop> {


    private final static String VIEW_NAME = "SHOPS";
    private static final String NAME = "NAME";
    private static final String ADRESS = "ADRESS";
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
                WHERE_LIKE.replace(PARAMETER, ADRESS)
                        .replace(VALUE, adress));
    }


    public ShopFinder findByID(int id) {
        ShopFinder finder  = (ShopFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, ID)
                        .replace(VALUE, Integer.toString(id)));
        return finder;
    }
}
