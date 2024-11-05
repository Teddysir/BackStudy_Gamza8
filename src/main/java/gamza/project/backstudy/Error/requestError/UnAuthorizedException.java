package gamza.project.backstudy.Error.requestError;

import gamza.project.backstudy.Error.ErrorCode;

public class UnAuthorizedException  extends BusinessException{

    public UnAuthorizedException(String message, ErrorCode code) {
        super(message, code);
    }
}
