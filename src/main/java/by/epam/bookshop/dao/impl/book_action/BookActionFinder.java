package by.epam.bookshop.dao.impl.book_action;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.dao.impl.book.BookFinder;
import by.epam.bookshop.entity.book_action.BookAction;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;


public class BookActionFinder extends EntityFinder<BookAction> {


    private final static String VIEW_NAME = "BOOK_ACTIONS";
    private static final String BOOK_ID = "BOOK_ID";
    private static final String BUYER_ID = "BUYER_ID";
    private static final String SELLER_ID = "SELLER_ID";
    private static final String SHOP_ID = "SELLER_ID";
    private static final String QUANTITY = "QUANTITY";
    private static final String INITIAL_STATUS = "INITIAL_STATUS";
    private static final String FINAL_STATUS = "FINAL_STATUS";
    private static final String DATE_TIME = "DATE_TIME";
    private static final String CURRENT_PRICE = "CURRENT_PRICE";

    private static final String ID = "ID";
    private static final String TIMESTAMP_FORMAT = "yyyy-mm-dd hh:mm:ss";

    public BookActionFinder() {
        super(VIEW_NAME);
    }

    public BookActionFinder findByID(int id) {
        BookActionFinder finder  = (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, ID)
                        .replace(VALUE, Integer.toString(id)));
        return finder;
    }

    public BookActionFinder findByBuyer(int id) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, BUYER_ID)
                        .replace(VALUE, Integer.toString(id)));
    }

    public BookActionFinder findByBook(int id) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, BOOK_ID)
                        .replace(VALUE, Integer.toString(id)));
    }

    public BookActionFinder findBySeller(int id) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, SELLER_ID)
                        .replace(VALUE, Integer.toString(id)));
    }

    public BookActionFinder findByShop(int id) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, SHOP_ID)
                        .replace(VALUE, Integer.toString(id)));
    }

    public BookActionFinder findByQuantityMore(Integer quantity) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, QUANTITY).replace(COMPARE, MORE)
                        .replace(VALUE, Float.toString(quantity)));
    }

    public BookActionFinder findByQuantityLess(Integer quantity) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, QUANTITY).replace(COMPARE, LESS)
                        .replace(VALUE, Float.toString(quantity)));
    }

    public BookActionFinder findByQuantity(Integer quantity) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, QUANTITY)
                        .replace(VALUE, Integer.toString(quantity)));
    }

    public BookActionFinder findByInitialStatus(Integer status) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, INITIAL_STATUS)
                        .replace(VALUE, Integer.toString(status)));
    }

    public BookActionFinder findByFinalStatus(Integer status) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, FINAL_STATUS)
                        .replace(VALUE, Integer.toString(status)));
    }

    public BookActionFinder findByDateLater(Timestamp dateTime) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, DATE_TIME).replace(COMPARE, MORE)
                        .replace(VALUE, new SimpleDateFormat(TIMESTAMP_FORMAT).format(dateTime)));
    }

    public BookActionFinder findByDateEarlier(LocalDateTime dateTime) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, DATE_TIME).replace(COMPARE, LESS)
                        .replace(VALUE, new SimpleDateFormat(TIMESTAMP_FORMAT).format(dateTime)));
    }

    public BookActionFinder findByPriceMore(Float price) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, CURRENT_PRICE).replace(COMPARE, MORE)
                        .replace(VALUE, Float.toString(price)));
    }

    public BookActionFinder findByPriceLess(Float price) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, CURRENT_PRICE).replace(COMPARE, LESS)
                        .replace(VALUE, Float.toString(price)));
    }

    public BookActionFinder findByPrice(Float price) {
        return (BookActionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, CURRENT_PRICE)
                        .replace(VALUE, Float.toString(price)));
    }
}
