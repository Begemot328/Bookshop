package by.epam.bookshop.entity.factory;

import by.epam.bookshop.entity.Entity;
import by.epam.bookshop.exceptions.FactoryException;

public  interface EntityFactory<T extends Entity> {

    String WRONG_INPUT_DATA = "Wrong input data";
    public T create(Object ... args) throws FactoryException;

    default String getWrongDataMessage() {
        return WRONG_INPUT_DATA;
    }
}
