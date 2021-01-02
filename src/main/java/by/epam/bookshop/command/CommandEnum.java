package by.epam.bookshop.command;

import by.epam.bookshop.command.impl.*;

public enum CommandEnum {
    EMPTY_COMMAND(new EmptyCommand()),

    OPTIMIZE_SHOP_COMMAND(new OptimizeShopCommand()),
    ADD_BOOK_COMMAND(new AddBookCommand()),
    ADD_BOOK_MENU_COMMAND(new AddBookMenuCommand()),
    ADD_POSITION_COMMAND(new AddPositionCommand()),
    ADD_POSITION_MENU_COMMAND(new AddPositionMenuCommand()),
    ADD_AUTHOR_COMMAND(new AddAuthorCommand()),
    ADD_AUTHOR_MENU_COMMAND(new ForwardCommand(JSPPages.ADD_AUTHOR_PAGE)),
    SEARCH_AUTHORS_COMMAND(new FindAuthorsCommand()),
    SEARCH_USERS_COMMAND(new FindUsersCommand()),
    SEARCH_SHOPS_COMMAND (new FindShopsCommand()),
    SEARCH_BOOKS_COMMAND(new FindBooksCommand()),

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

    CHANGE_PAGE_COMMAND(new ChangePageCommand()),
    CHANGE_LOCALE_COMMAND(new ChangeLocaleCommand()),

    LOGIN_COMMAND(new LoginCommand()),
    LOGOUT_COMMAND(new LogOutCommand()),
    SIGNIN_COMMAND(new ForwardCommand(JSPPages.LOGIN_PAGE)),
    REGISTER_MENU_COMMAND(new ForwardCommand(JSPPages.REGISTER_PAGE)),
    REGISTER_COMMAND(new RegisterCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

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

    public Command getCommand() {
        return command;
    }

}
