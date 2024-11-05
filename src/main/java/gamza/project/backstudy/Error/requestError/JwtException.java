package gamza.project.backstudy.Error.requestError;

import gamza.project.backstudy.Error.ErrorCode;

public class JwtException extends BusinessException {

    public JwtException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}