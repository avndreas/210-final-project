package model.exceptions;

public class EntityAlreadyExistsException extends Exception {
    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }
}
