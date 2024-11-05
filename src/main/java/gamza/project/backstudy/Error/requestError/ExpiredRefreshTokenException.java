package gamza.project.backstudy.Error.requestError;

import gamza.project.backstudy.Error.ErrorCode;

public class ExpiredRefreshTokenException extends BusinessException {

    public ExpiredRefreshTokenException(String message, ErrorCode code) {
        super(message, code);
    }
}