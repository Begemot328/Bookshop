package by.epam.bookshop.command;

/**
 * Class JSP pages names
 *
 * @author Yury Zmushko
 * @version 1.0
 */
public enum JSPPages {
  START_PAGE("WEB-INF/jsp/searchBooks.jsp"),
  ERROR_PAGE("WEB-INF/jsp/error.jsp"),

  SEARCH_BOOKS_PAGE("WEB-INF/jsp/searchBooks.jsp"),
  SEARCH_AUTHORS_PAGE("WEB-INF/jsp/searchAuthors.jsp"),
  SEARCH_SHOPS_PAGE("WEB-INF/jsp/searchShops.jsp"),
  SEARCH_USERS_PAGE("WEB-INF/jsp/searchUsers.jsp"),

  ADD_POSITION_PAGE("WEB-INF/jsp/addPosition.jsp"),
  ADD_BOOK_PAGE("WEB-INF/jsp/addBook.jsp"),
  ADD_SHOP_PAGE("WEB-INF/jsp/addShop.jsp"),
  EDIT_POSITION_PAGE("WEB-INF/jsp/editPosition.jsp"),
  EDIT_BOOK_PAGE("WEB-INF/jsp/editBook.jsp"),
  EDIT_SHOP_PAGE("WEB-INF/jsp/editShop.jsp"),
  ADD_AUTHOR_PAGE("WEB-INF/jsp/addAuthor.jsp"),
  EDIT_AUTHOR_PAGE("WEB-INF/jsp/editAuthor.jsp"),
  REGISTER_PAGE("WEB-INF/jsp/register.jsp"),
  LOGIN_PAGE("WEB-INF/jsp/login.jsp"),
  VIEW_BOOK_PAGE("WEB-INF/jsp/book.jsp"),
  VIEW_AUTHOR_PAGE("WEB-INF/jsp/author.jsp"),
  VIEW_SHOP_PAGE("WEB-INF/jsp/shop.jsp"),
  VIEW_USER_PAGE("WEB-INF/jsp/user.jsp"),
  VIEW_POSITION_PAGE("WEB-INF/jsp/position.jsp"),
  PROCESS_POSITION_PAGE("WEB-INF/jsp/positionSeller.jsp"),
  BOOK_POSITION_PAGE("WEB-INF/jsp/positionBuyer.jsp");

    private String page;

    /**
     * Constructor
     *
     * @param page - {@link Command} to add.
     */
    JSPPages (String page) {
        this.page = page;
    }

    public String getPage() {
        return this.page;
    }
}
