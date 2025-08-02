package cc.sika.file.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class R<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    public static <T> R<T> success(T data) {
        return success(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> R<T> success(String message, T data) {
        return success(HttpStatus.OK.value(), message, data);
    }

    public static <T> R<T> success(Integer code, String message, T data) {
        return new R<>(code, message, data);
    }

    public static <T> R<T> success() {
        return success(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
    }

    public static <T> R<T> fail(Integer code, String message) {
        return new R<>(code, message, null);
    }

    public static <T> R<T> fail() {
        return fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

}
