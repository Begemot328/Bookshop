package by.epam.bookshop.command;

import by.epam.bookshop.command.impl.EmptyCommand;
import by.epam.bookshop.command.impl.FindAllBooksCommand;
import by.epam.bookshop.command.impl.FindBooksCommand;

public enum CommandEnum {
    EMPTY_COMMAND(new EmptyCommand()), SEARCH_BOOKS(new FindBooksCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    static Command getCommand(String commandName) {
        Command command = valueOf(commandName).getCommand();
        if (command == null) {
            command = new EmptyCommand();
        }
        return command;
    }

    public Command getCommand() {
        return command;
    }

}
