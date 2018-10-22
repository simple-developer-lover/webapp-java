package com.cn.webapp.test;

public class TestBean {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] args) {
		HSFSearchPool.submit(new Runnable() {

			@Override
			public void run() {

			}
		});
		HSFSearchPool.submit(new Runnable() {

			@Override
			public void run() {

			}
		});
	}
}
