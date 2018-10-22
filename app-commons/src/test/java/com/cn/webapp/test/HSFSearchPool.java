package com.cn.webapp.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class HSFSearchPool {
	final static ExecutorService es = Executors.newFixedThreadPool(5);
	final static BlockingQueue<Runnable> taskQueue = new LinkedBlockingDeque<>();
	static {
		System.out.println("1111");
		start();
	}

	public synchronized static void submit(Runnable run) {
		try {
			if (taskQueue.size() > 1000) {
				return;
			}
			taskQueue.put(run);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void start() {
		new Thread() {
			public void run() {
				while (true) {
					Runnable r = HSFSearchPool.taskQueue.poll();
					if (r != null) {
						es.submit(r);
					} else {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
					}
				}

			}
		}.start();
	}
	
}
