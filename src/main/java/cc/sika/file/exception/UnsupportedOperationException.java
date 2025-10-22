package cc.sika.file.exception;

/**
 * 不支持的操作
 *
 * @author 小吴来哩
 * @since 2025-09
 */
@SuppressWarnings("unused")
public class UnsupportedOperationException extends BaseRuntimeException {

    public UnsupportedOperationException() {
        super("不支持的操作");
    }

    public UnsupportedOperationException(String message) {
        super(message);
    }
}
