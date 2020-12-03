package by.epam.bookshop.entity;

import by.epam.bookshop.exceptions.FactoryException;

public  interface EntityFactory<T extends Entity> {

    String WRONG_INPUT_DATA = "Wrong input data";
    T create(Object ... args) throws FactoryException;

    default String getWrongDataMessage() {
        return WRONG_INPUT_DATA;
    }

    default T createWithID(int id, Object... args) throws FactoryException {
        T t = create(args);
        t.setId(id);
        return t;
    }
}
