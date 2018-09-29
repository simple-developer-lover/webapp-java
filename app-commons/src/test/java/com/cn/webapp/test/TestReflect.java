package com.cn.webapp.test;

import java.lang.reflect.Method;

import com.esotericsoftware.reflectasm.MethodAccess;

public class TestReflect {
	public static void main(String[] args) {
		TestBean bean = new TestBean();
		bean.setName("1111111");
		String format = "test %s method ...for %dms";
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			bean.getName();
		}
		System.out.println(String.format(format, "get method", System.currentTimeMillis() - startTime));
		System.out.println("==========================");

		startTime = System.currentTimeMillis();
		try {
			Method m = bean.getClass().getMethod("getName");
			for (int i = 0; i < 100000000; i++) {
				m.invoke(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(String.format(format, "reflect cache method", System.currentTimeMillis() - startTime));
		System.out.println("==========================");
		
		startTime = System.currentTimeMillis();
		MethodAccess access = MethodAccess.get(TestBean.class);// 生成字节码的方式创建UserServiceMethodAccess
		for (int i = 0; i < 100000000; i++) {
			access.invoke(bean, "getName");
		}
		System.out.println(String.format(format, "reflect cache method", System.currentTimeMillis() - startTime));
		System.out.println("==========================");

	}
}
