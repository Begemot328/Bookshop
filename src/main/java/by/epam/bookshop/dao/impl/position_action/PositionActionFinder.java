package by.epam.bookshop.dao.impl.position_action;

import by.epam.bookshop.dao.EntityFinder;
import by.epam.bookshop.entity.position_action.PositionAction;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;


public class PositionActionFinder extends EntityFinder<PositionAction> {


    private final static String VIEW_NAME = "POSITION_ACTIONS";
    private static final String INITIAL_POSITION_ID = "INITIAL_POSITION_ID";
    private static final String FINAL_POSITION_ID = "FINAL_POSITION_ID";
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

    public PositionActionFinder() {
        super(VIEW_NAME);
    }

    public PositionActionFinder findByBuyer(int id) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, BUYER_ID)
                        .replace(VALUE, Integer.toString(id)));
    }

    public PositionActionFinder findByPosition(int id) {
        return ((PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, INITIAL_POSITION_ID)
                        .replace(VALUE, Integer.toString(id))
                + OR.replace(PARAMETER, FINAL_POSITION_ID)
                .replace(VALUE, Integer.toString(id))));
    }

    public PositionActionFinder findBySeller(int id) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, SELLER_ID)
                        .replace(VALUE, Integer.toString(id)));
    }

    public PositionActionFinder findByShop(int id) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE_LIKE.replace(PARAMETER, SHOP_ID)
                        .replace(VALUE, Integer.toString(id)));
    }

    public PositionActionFinder findByQuantityMore(Integer quantity) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, QUANTITY).replace(COMPARE, MORE)
                        .replace(VALUE, Float.toString(quantity)));
    }

    public PositionActionFinder findByQuantityLess(Integer quantity) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, QUANTITY).replace(COMPARE, LESS)
                        .replace(VALUE, Float.toString(quantity)));
    }

    public PositionActionFinder findByQuantity(Integer quantity) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, QUANTITY)
                        .replace(VALUE, Integer.toString(quantity)));
    }

    public PositionActionFinder findByInitialStatus(Integer status) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, INITIAL_STATUS)
                        .replace(VALUE, Integer.toString(status)));
    }

    public PositionActionFinder findByFinalStatus(Integer status) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, FINAL_STATUS)
                        .replace(VALUE, Integer.toString(status)));
    }

    public PositionActionFinder findByDateLater(Timestamp dateTime) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, DATE_TIME).replace(COMPARE, MORE)
                        .replace(VALUE, new SimpleDateFormat(TIMESTAMP_FORMAT).format(dateTime)));
    }

    public PositionActionFinder findByDateEarlier(LocalDateTime dateTime) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, DATE_TIME).replace(COMPARE, LESS)
                        .replace(VALUE, new SimpleDateFormat(TIMESTAMP_FORMAT).format(dateTime)));
    }

    public PositionActionFinder findByPriceMore(Float price) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, CURRENT_PRICE).replace(COMPARE, MORE)
                        .replace(VALUE, Float.toString(price)));
    }

    public PositionActionFinder findByPriceLess(Float price) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE_COMPARING.replace(PARAMETER, CURRENT_PRICE).replace(COMPARE, LESS)
                        .replace(VALUE, Float.toString(price)));
    }

    public PositionActionFinder findByPrice(Float price) {
        return (PositionActionFinder) this.findBy(SQL_QUERY +
                WHERE.replace(PARAMETER, CURRENT_PRICE)
                        .replace(VALUE, Float.toString(price)));
    }
}
