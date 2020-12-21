package by.epam.bookshop.command;

import by.epam.bookshop.command.impl.*;

public enum CommandEnum {
    EMPTY_COMMAND(new EmptyCommand()),
    SEARCH_BOOKS_COMMAND(new FindBooksCommand()),
    LOGIN_COMMAND(new LoginCommand()),
    SIGNIN_COMMAND(new ForwardCommand(JSPPages.LOGIN_PAGE)),
    REGISTER_MENU_COMMAND(new ForwardCommand(JSPPages.REGISTER_PAGE)),
    CHANGE_LOCALE_COMMAND(new ChangeLocaleCommand()),
    LOGOUT_COMMAND(new LogOutCommand()),
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
