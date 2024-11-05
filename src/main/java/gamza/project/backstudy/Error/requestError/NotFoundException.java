package gamza.project.backstudy.Error.requestError;

import gamza.project.backstudy.Error.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}