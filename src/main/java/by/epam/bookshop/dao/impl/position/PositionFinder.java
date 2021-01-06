package by.epam.bookshop.dao.impl.position;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.book.Book;
import by.epam.bookshop.entity.position.Position;


public class PositionFinder extends EntityFinder<Position> {


    private final static String VIEW_NAME = "POSITIONS";
    private static final String ID = "ID";
    private static final String BOOK_ID = "BOOK_ID";
    private static final String SHOP_ID = "SHOP_ID";
    private static final String POSITION_STATUS = "POSITION_STATUS";
    private static final String NOTE = "NOTE";
    private static final String QUANTITY = "QUANTITY";


    public PositionFinder() {
        super(VIEW_NAME);
    }

    public PositionFinder findByBook(int bookId) {
        return (PositionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, BOOK_ID)
                        .replace(VALUE, Integer.toString(bookId)));
    }

    public PositionFinder findByShop(int shopId) {
        return (PositionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, SHOP_ID)
                        .replace(VALUE, Integer.toString(shopId)));
    }

    public PositionFinder findByStatus(int statusId) {
        return (PositionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, POSITION_STATUS)
                        .replace(VALUE, Integer.toString(statusId)));
    }

    public PositionFinder findByQuantityMore(int quantity) {
        return (PositionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, QUANTITY).replace(COMPARE, MORE)
                        .replace(VALUE, Float.toString(quantity)));
    }

    public PositionFinder findByQuantityLess(int quantity) {
        return (PositionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, QUANTITY).replace(COMPARE, LESS)
                        .replace(VALUE, Float.toString(quantity)));
    }

    public PositionFinder findByQuantity(int quantity) {
        return (PositionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, QUANTITY)
                        .replace(VALUE, Float.toString(quantity)));
    }
}
