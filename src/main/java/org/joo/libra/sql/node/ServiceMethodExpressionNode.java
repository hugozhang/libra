package org.joo.libra.sql.node;

import lombok.Data;
import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.HasList;
import org.joo.libra.common.HasValue;
import org.joo.libra.pointer.RpcPredicate;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ServiceMethodExpressionNode implements ExpressionNode, HasValue<Object> {

	private String serviceName;

	private String methodName;

	private HasList inner;

	@Override
	public Predicate buildPredicate() {
		return new RpcPredicate(this);
	}

	@Override
	public Object getValue(final PredicateContext context) {

		ApplicationContext springContext = context.getSpringContext();
		if (springContext == null) {
			throw new IllegalArgumentException("No spring context found in the context");
		}
		Object bean = springContext.getBean(serviceName);
		if (bean == null) {
			throw new IllegalArgumentException("No bean found with name " + serviceName);
		}

		Object[] args = getArgs(context);

		Class<?> beanClass = bean.getClass();

		Class<?>[] argsClass = getArgsClass(args);

		try {
			Method method = beanClass.getDeclaredMethod(methodName, argsClass);
			return method.invoke(bean, args);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("No method found with name " + methodName + ",args class " + Arrays.stream(argsClass).map(Class::getName).collect(Collectors.toList())  +" in bean " + serviceName);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Illegal access to method " + methodName + " in bean " + serviceName);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException("Error invoking method " + methodName + " in bean " + serviceName);
		}
	}

	private Class<?>[] getArgsClass(Object[] args) {
		List<Class<?>> argClasses = new ArrayList<>();
		for (Object arg : args) {
			if (arg != null) {
				argClasses.add(arg.getClass());
			} else {
				argClasses.add(Object.class);
			}
		}
		return argClasses.toArray(new Class<?>[0]);
	}


	public Object[] getArgs(final PredicateContext context) {
		if (inner == null)
			return new Object[0];
		return inner.getValueAsArray(context);
	}

	public String toString() {
		return serviceName + "." + methodName + "(" + inner + ")";
	}
}