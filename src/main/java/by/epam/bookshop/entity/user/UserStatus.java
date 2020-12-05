package by.epam.bookshop.entity.user;

import by.epam.bookshop.exceptions.UnknownEntityException;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum UserStatus {
    NON_EXISTENT(0), DISABLED (1), BUYER (2), SELLER (3), ADMIN(4), COURIER(5);

    private final Integer id;

     UserStatus(Integer id) {
        this.id = id;
    }

    /**
     *
     * @throws UnknownEntityException if such id does not exist
     */
    public static UserStatus resolveById(int id) throws UnknownEntityException {
        UserStatus[] ranks = UserStatus.values();
        Stream<UserStatus> stream = Arrays.stream(ranks);
        Optional<UserStatus> result =  stream.filter((rank) -> rank.getId() == id)
                .findAny();
        if(result.isPresent()) {
            return result.get();
        } else {
            throw  new UnknownEntityException("No entity with id " + id);
        }
    }

    public int getId() {
        return id;
    }
}
