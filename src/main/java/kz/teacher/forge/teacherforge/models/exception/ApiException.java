package kz.teacher.forge.teacherforge.models.exception;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 6803391683871204802L;

    private final ApiError apiError;
    private final ErrorResponse errorResponse;

    public ApiException(ApiError apiError, String message) {
        super(apiError.name() + ": " + message);
        this.errorResponse = new ErrorResponse(apiError.name(), message);
        this.apiError = apiError;
    }

    /**
     * Creates {@link ApiException}
     *
     * @param apiError        {@link ApiError} type of error
     * @param message         message
     * @param addToErrorsData if true, then apiError and message will be added to {@link ErrorResponse#getData()}
     */
    public ApiException(ApiError apiError, String message, boolean addToErrorsData) {
        this(apiError, message);
        if (addToErrorsData) {
            this.set(apiError.name(), message);
        }
    }

    public ApiException(ApiError apiError) {
        super(apiError.name() + ": " + apiError.getMessage());
        this.errorResponse = new ErrorResponse(apiError.name(), apiError.getMessage());
        this.apiError = apiError;
    }

    public ApiException(ApiError apiError, ErrorResponse errorResponse) {
        super(apiError.name() + ": " + errorResponse.getDescription());
        this.errorResponse = errorResponse;
        this.apiError = apiError;
    }

    public void set(String key, Object value) {
        if (this.errorResponse.getData() == null) {
            this.errorResponse.setData(new HashMap<>());
        }
        this.errorResponse.getData().put(key, value);
    }

    public void setErrors(Map<String, Object> data) {
        this.errorResponse.setData(data);
    }

    public ApiError getApiError() {
        return apiError;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    @Override
    public String getMessage() {
        if (errorResponse == null) {
            return null;
        }

        return errorResponse.getCode() + ": " + errorResponse.getDescription();
    }

}

