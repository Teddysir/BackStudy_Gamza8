package gamza.project.backstudy.Error.requestError;

import gamza.project.backstudy.Error.ErrorCode;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}