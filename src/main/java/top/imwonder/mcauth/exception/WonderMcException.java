package top.imwonder.mcauth.exception;

import org.springframework.http.HttpStatus;

public class WonderMcException extends RuntimeException {

    private ErrorBody errorBody;

    private HttpStatus status;

    private WonderMcException(String error, Throwable caluse) {
        super(error, caluse);
        this.errorBody = new ErrorBody(error);
    }

    public ErrorBody getErrorBody() {
        return errorBody;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static WonderMcException illegalArgumentException(String errorMessage) {
        return illegalArgumentException(errorMessage, null);
    }

    public static WonderMcException illegalArgumentException(String errorMessage, Throwable caluse) {
        WonderMcException exception = new WonderMcException("IllegalArgumentException", caluse);
        exception.errorBody.errorMessage = errorMessage;
        exception.status = HttpStatus.BAD_REQUEST;
        return exception;
    }

    public static WonderMcException forbiddenOperationException(String errorMessage) {
        return forbiddenOperationException(errorMessage, null);
    }

    public static WonderMcException forbiddenOperationException(String errorMessage, Throwable caluse) {
        WonderMcException exception = new WonderMcException("ForbiddenOperationException", caluse);
        exception.errorBody.errorMessage = errorMessage;
        exception.status = HttpStatus.FORBIDDEN;
        return exception;
    }

    public static WonderMcException otherWonderMcException(HttpStatus status, String errorMessage) {
        return otherWonderMcException(status, errorMessage, null);
    }

    public static WonderMcException otherWonderMcException(HttpStatus status, String errorMessage, Throwable caluse) {
        WonderMcException exception = new WonderMcException(status.getReasonPhrase(), caluse);
        exception.errorBody.errorMessage = errorMessage;
        exception.status = status;
        return exception;
    }

    public class ErrorBody {

        private String error;

        private String errorMessage;

        private ErrorBody(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
