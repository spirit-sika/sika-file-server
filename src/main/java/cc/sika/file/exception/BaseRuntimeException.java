package cc.sika.file.exception;

/**
 * 基础公共运行时异常
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@SuppressWarnings("unused")
public class BaseRuntimeException extends RuntimeException {
    public BaseRuntimeException() {
    }

    public BaseRuntimeException(String message) {
        super(message);
    }
}
