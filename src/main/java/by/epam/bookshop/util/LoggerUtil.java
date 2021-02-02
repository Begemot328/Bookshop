package by.epam.bookshop.util;

import by.epam.bookshop.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    static Logger logger = LoggerFactory.getLogger(Controller.class);

    public static Logger getLoggerInstance() {
        return logger;
    }
}
