package model.Exceptions;

public class EntityAlreadyExistsException extends Exception {
    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }
}
