package pl.com.gregus.exception;

/**
 *
 * @author Grzegorz Guściora
 * @version 1.0
 * @created 2013-12-09 10:14:04
 */
public class LibrarianRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 4700017591372788822L;

    public LibrarianRuntimeException() {
        super();
    }

    public LibrarianRuntimeException(String message) {
        super(message);
    }

    public LibrarianRuntimeException(Throwable cause) {
        super(cause);
    }

    public LibrarianRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
