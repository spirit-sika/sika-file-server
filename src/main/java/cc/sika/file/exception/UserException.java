package cc.sika.file.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author 小吴来哩
 * @since 2025-03
 */
@Getter
public class UserException extends RuntimeException {
    
    private final Integer code;
    
    public UserException() {
        super("操作用户信息失败");
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @SuppressWarnings("unused")
    public UserException(String message) {
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
    
    public UserException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
