package by.epam.bookshop.command;

import by.epam.bookshop.command.impl.*;

/**
 * Commands enum.
 *
 * @author Yury Zmushko
 * @version 1.0
 */
public enum CommandEnum {
/**
 * Commands enum
 */
    EMPTY_COMMAND(new FindBooksCommand()),

    OPTIMIZE_SHOP_COMMAND(new OptimizeShopCommand()),
    ADD_BOOK_COMMAND(new AddBookCommand()),
    EDIT_BOOK_COMMAND(new EditBookCommand()),
    ADD_BOOK_MENU_COMMAND(new AddBookMenuCommand()),
    EDIT_BOOK_MENU_COMMAND(new EditBookMenuCommand()),
    ADD_POSITION_COMMAND(new AddPositionCommand()),
    ADD_POSITION_MENU_COMMAND(new AddPositionMenuCommand()),
    ADD_AUTHOR_COMMAND(new AddAuthorCommand()),
    ADD_AUTHOR_MENU_COMMAND(new ForwardCommand(JSPPages.ADD_AUTHOR_PAGE)),
    ADD_SHOP_COMMAND(new AddShopCommand()),
    ADD_SHOP_MENU_COMMAND(new ForwardCommand(JSPPages.ADD_SHOP_PAGE)),
    EDIT_POSITION_COMMAND(new EditPositionCommand()),
    EDIT_POSITION_MENU_COMMAND(new EditPositionMenuCommand()),
    EDIT_AUTHOR_COMMAND(new EditAuthorCommand()),
    EDIT_AUTHOR_MENU_COMMAND(new EditAuthorMenuCommand()),
    EDIT_SHOP_COMMAND(new EditShopCommand()),
    EDIT_SHOP_MENU_COMMAND(new EditShopMenuCommand()),
    SEARCH_AUTHORS_COMMAND(new FindAuthorsCommand()),
    SEARCH_USERS_COMMAND(new FindUsersCommand()),
    SEARCH_SHOPS_COMMAND (new FindShopsCommand()),
    SEARCH_BOOKS_COMMAND(new FindBooksCommand()),
    MANAGE_GENRES_COMMAND(new GenreManageCommand()),
    GENRE_MENU_COMMAND(new ForwardCommand(JSPPages.GENRE_MENU_PAGE)),
    PROCESS_POSITION_COMMAND(new ProcessPositionCommand()),
    BOOK_BOOK_COMMAND(new BookBookCommand()),
    CANCEL_BOOK_COMMAND(new CancelBookCommand()),
    RETURN_BOOK_COMMAND(new ReturnBookCommand()),
    SELL_BOOK_COMMAND(new SellBookCommand()),
    VIEW_POSITION_COMMAND(new ViewPositionCommand()),
    VIEW_BOOK_COMMAND(new ViewBookCommand()),
    VIEW_AUTHOR_COMMAND(new ViewAuthorCommand()),
    VIEW_USER_COMMAND(new ViewUserCommand()),
    VIEW_SHOP_COMMAND(new ViewShopCommand()),
    CHANGE_LOCALE_COMMAND(new ChangeLocaleCommand()),
    LOGIN_COMMAND(new LoginCommand()),
    LOGOUT_COMMAND(new LogOutCommand()),
    SIGNIN_COMMAND(new ForwardCommand(JSPPages.LOGIN_PAGE)),
    REGISTER_MENU_COMMAND(new ForwardCommand(JSPPages.REGISTER_PAGE)),
    REGISTER_COMMAND(new RegisterCommand()),
    GET_VALUES_COMMAND(new GetValuesCommand());
    private Command command;

    /**
     * Constructor
     *
     * @param command - {@link Command} to add.
     */
    CommandEnum(Command command) {
        this.command = command;
    }

    /**
     * {@link Command} retriever
     *
     * @param commandName - Name of the command to get. Must be
     *                    upper-case with under undersore as delimeter. If @null
     *                    - returns default command - FindBooksCommand()
     * @return corresponding {@link Command} object
     */
    public static Command getCommand(String commandName) {
        Command command = null;

        if (commandName == null || commandName.isEmpty()) {
            return new FindBooksCommand();
        }
        CommandEnum commandEnum;

        try {
            commandEnum = valueOf(commandName);
            command = commandEnum.getCommand();

        } catch (IllegalArgumentException e) {
            command= new FindBooksCommand();
        }

        return command;
    }

    /**
     * {@link Command} getter
     *
     * @return corresponding {@link Command} object
     */
    public Command getCommand() {
        return command;
    }

}
