package cc.sika.file.exception;

import cn.hutool.core.text.CharSequenceUtil;

import java.util.Objects;

/**
 * bean操作与表格操作异常
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@SuppressWarnings("unused")
public class BeanTableException extends BaseRuntimeException {
	
	private final Class<?> beanClass;
	
	public BeanTableException(String message, Class<?> clazz) {
		super(message);
		this.beanClass = clazz;
	}
	
	public BeanTableException(String message) {
		super(message);
		this.beanClass = null;
	}
	
	public BeanTableException(Class<?> clazz) {
		super(CharSequenceUtil.format("尝试内省类型为:[{}]的bean失败", clazz.getSimpleName()));
		this.beanClass = clazz;
	}
	
	@Override
	public String getMessage() {
		if (Objects.isNull(beanClass)) {
			return super.getMessage();
		}
		return super.getMessage() + ", 目标类为: " + beanClass.getName();
	}
}
