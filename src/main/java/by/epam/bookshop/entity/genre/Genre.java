package by.epam.bookshop.entity.genre;

import by.epam.bookshop.entity.Entity;

import java.util.Objects;

public class Genre extends Entity {
    private static final String GENRE = "genre.";

    private String name;
    private String nameKey;

    public Genre(String name, String nameKey) {
        this.name = name;
        this.nameKey = nameKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKey() {
        return GENRE.concat(nameKey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return name.equals(genre.name)
                && nameKey.equals(genre.nameKey)
                && id == genre.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nameKey);
    }
}
