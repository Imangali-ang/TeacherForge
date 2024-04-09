package kz.teacher.forge.teacherforge.models.exception;
import lombok.Getter;

public enum ApiError {
    UNAUTHORIZED(HttpStatus.SC_UNAUTHORIZED, "User is not authorized"),
    AUTHORIZATION_ERROR(HttpStatus.SC_UNAUTHORIZED, "Authorization error"),
    ARGUMENT_MISSING(HttpStatus.SC_BAD_REQUEST, "Body argument missing"),
    HEADER_MISSING(HttpStatus.SC_BAD_REQUEST, "Header argument missing"),
    BAD_RESOURCE_ID(HttpStatus.SC_BAD_REQUEST, "Bad resource id"),
    RESOURCE_NOT_FOUND(HttpStatus.SC_NOT_FOUND, "No resource found"),
    RESOURCE_BLOCKED(HttpStatus.SC_FORBIDDEN, "Too much attempts, please try later"),
    RESOURCE_EXISTS(HttpStatus.SC_CONFLICT, "Such resource already exists"),
    FORBIDDEN(HttpStatus.SC_FORBIDDEN, "Forbidden"),
    BAD_REQUEST(HttpStatus.SC_BAD_REQUEST, "Bad Request"),
    INVALID_GRANT(HttpStatus.SC_UNAUTHORIZED, "Bad credentials"),
    TOKEN_EXPIRED(HttpStatus.SC_UNAUTHORIZED, "Token is expired"),
    INVALID_PASSWORD(HttpStatus.SC_FORBIDDEN,"Invalid password"),
    USER_BLOCKED(HttpStatus.SC_FORBIDDEN, "User is blocked"),
    INTERNAL_SERVER_ERROR(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Internal server error"),;

    @Getter
    private final int status;
    @Getter
    private final String message;


    ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
