package by.epam.bookshop.command;

import by.epam.bookshop.command.impl.*;

public enum CommandEnum {
    EMPTY_COMMAND(new EmptyCommand()),

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
    VIEW_SHOP_COMMAND(new ViewShopCommand()),

    CHANGE_PAGE_COMMAND(new ChangePageCommand()),
    CHANGE_LOCALE_COMMAND(new ChangeLocaleCommand()),

    LOGIN_COMMAND(new LoginCommand()),
    LOGOUT_COMMAND(new LogOutCommand()),
    SIGNIN_COMMAND(new ForwardCommand(JSPPages.LOGIN_PAGE)),
    REGISTER_MENU_COMMAND(new ForwardCommand(JSPPages.REGISTER_PAGE)),
    REGISTER_COMMAND(new LoginCommand());

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
