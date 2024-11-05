package gamza.project.backstudy.Error.requestError;

import gamza.project.backstudy.Error.ErrorCode;

public class DuplicateException extends BusinessException {

    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}