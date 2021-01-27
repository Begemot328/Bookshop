package by.epam.bookshop.entity.genre;

import by.epam.bookshop.entity.Entity;

import java.util.Objects;

public class Genre extends Entity {
    private String name;


    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        if (id != genre.id) return false;
        return name.equals(genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
