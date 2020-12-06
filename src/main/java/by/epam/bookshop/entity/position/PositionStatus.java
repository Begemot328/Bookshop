package by.epam.bookshop.entity.position;

import by.epam.bookshop.entity.user.UserStatus;
import by.epam.bookshop.exceptions.UnknownEntityException;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum PositionStatus {
    NON_EXISTENT(0), READY(1), SOLD(2), RESERVED(3), TRANSFER(4);


    private final Integer id;

    PositionStatus(Integer id) {
        this.id = id;
    }

    /**
     *
     * @throws UnknownEntityException if such id does not exist
     */
    public static PositionStatus resolveById(int id) throws UnknownEntityException {
        PositionStatus[] statuses = PositionStatus.values();
        Stream<PositionStatus> stream = Arrays.stream(statuses);
        Optional<PositionStatus> result =  stream.filter((status) -> status.getId() == id)
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
