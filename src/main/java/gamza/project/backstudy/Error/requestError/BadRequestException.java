package gamza.project.backstudy.Error.requestError;

import gamza.project.backstudy.Error.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}