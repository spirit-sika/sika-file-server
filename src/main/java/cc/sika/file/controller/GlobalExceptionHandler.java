package cc.sika.file.controller;

import cc.sika.file.entity.vo.R;
import cc.sika.file.exception.BeanTableException;
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

}
