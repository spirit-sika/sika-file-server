package cc.sika.file.exception;

import lombok.Getter;

@SuppressWarnings("unused")
public class AuthException extends BaseRuntimeException {

    @Getter
    private final int code;

    public AuthException(int code) {
        this(code, "鉴权失败!");
    }

    public AuthException(int code, String message) {
        super(message);
        this.code = code;
    }
}
