package indi.monkey.webapp.commons.loader;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.esotericsoftware.reflectasm.MethodAccess;

import indi.monkey.webapp.commons.annotation.HandlerMethod;

public class MethodAccessLoader {

	private Map<String, Integer> methods_index;
	private MethodAccess access;
	private Object obj;
	transient private static final String BREAK_POINT = "-";

	/**
	 * 
	 * @param obj
	 */
	public MethodAccessLoader(Object obj) {
		this.obj = obj;
		Class<?> type = obj.getClass();
		access = MethodAccess.get(type);
		HandlerMethod handlerMethod = obj.getClass().getAnnotation(HandlerMethod.class);
		final String suffix = handlerMethod == null ? "" : handlerMethod.value();
		Method[] ms = type.getDeclaredMethods();
		methods_index = Arrays.stream(ms).filter(m -> {
			return !Modifier.isPrivate(m.getModifiers()) && m.getAnnotation(HandlerMethod.class) != null;
		}).collect(Collectors.toMap(
				m -> ("".equals(suffix) ? "" : suffix + BREAK_POINT) + m.getAnnotation(HandlerMethod.class).value(),
				m -> access.getIndex(m.getName())));
	}

	/**
	 * 
	 * @param actionType
	 * @param resultType
	 * @param args
	 * @return
	 */
	public <T> T invoke(String actionType, Class<T> resultType, Object... args) {
		return resultType.cast(access.invoke(obj, methods_index.get(actionType), args));
	}

	public boolean contains(String actionType) {
		return methods_index.containsKey(actionType);
	}
}
