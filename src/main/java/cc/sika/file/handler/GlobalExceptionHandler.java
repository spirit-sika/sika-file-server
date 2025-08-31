package cc.sika.file.handler;

import cc.sika.file.entity.vo.R;
import cc.sika.file.exception.AuthException;
import cc.sika.file.exception.BaseRuntimeException;
import cc.sika.file.exception.BeanTableException;
import cc.sika.file.exception.UserException;
import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public R<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return R.fail(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
    }

    @ExceptionHandler(BeanTableException.class)
    public R<Object> handleBeanTableException(BeanTableException ignoredEx) {
        return R.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器发生错误");
    }

    @ExceptionHandler(UserException.class)
    public R<Object> handleUserException(UserException userException) {
        return R.fail(userException.getCode(), userException.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public R<Object> handleAuthException(AuthException authException) {
        return R.fail(authException.getCode(), authException.getMessage());
    }

    @ExceptionHandler(BaseRuntimeException.class)
    public R<Object> handleBaseRuntimeException(BaseRuntimeException baseRuntimeException) {
        return R.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), baseRuntimeException.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public R<Object> handleNotLoginException() {
        return R.fail(HttpStatus.UNAUTHORIZED.value(), "请先登录");
    }

}
