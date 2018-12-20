package indi.monkey.webapp.commons.pub.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Thread4ViableResult {

	public static <T> T execute(Collection<Callable<T>> calls, int serviceSize, Predicate<T> p) {
		ExecutorService es = Executors.newFixedThreadPool(serviceSize < 0 ? calls.size() : serviceSize);
		final BlockingQueue<Future<T>> queue = new LinkedBlockingQueue<>(calls.size());
		final CompletionService<T> service = new ExecutorCompletionService<>(es, queue);
		T t = null;
		calls.forEach(c -> {
			service.submit(c);
		});
		try {
			for (int i = 0; i < calls.size(); i++) {
				t = service.take().get();
				if (p != null) {
					if (p.test(t))
						break;
				} else {
					if (t != null)
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			es.shutdownNow();
			queue.clear();
		}
		return t;
	}

	private static final BlockingQueue<Runnable> QUEUE = new LinkedBlockingQueue<>(1000);

	private static final Object LOCK = new Object();
	static {
		startWorkThread(5);
	}

	public static void addTask(Runnable runnable) {
		if (runnable != null && QUEUE.size() < 1000) {
			try {
				synchronized (LOCK) {
					QUEUE.put(runnable);
					LOCK.notifyAll();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void startWorkThread(int threadSize) {
		if (threadSize < 0) {
			throw new IllegalArgumentException("" + threadSize);
		}
		for (int i = 0; i < threadSize; i++) {
			new WorkThread(i).start();
		}
	}

	private static final class WorkThread extends Thread {
		private int id;

		public WorkThread(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
				count++;
			}
			// System.out.println(String.format(">>>>>>>> id:%s thread in running", id));
		}
	}

	static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.SECONDS, QUEUE,
			new DiscardPolicy());

	private volatile static int count = 0;

	public static void test() {
		for (int i = 0; i < 1000; i++) {
			// long start = System.currentTimeMillis();
			threadPoolExecutor.submit(new WorkThread(i));
			// System.out.println(String.format(">>>>>>>> add task cost:%s ms",
			// (System.currentTimeMillis() - start)));
		}
		System.err.println(">>>> sleep for 3000 ms ...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 1000; i++) {
			threadPoolExecutor.submit(new WorkThread(i));
		}
		while (QUEUE.size() > 0) {
			System.out.println(String.format(">>>>> task queue size:%s", QUEUE.size()));
		}
		System.out.println(String.format("execute for %s times", count));
	}

	public static void main(String[] args) throws InterruptedException {
		test();
	}

}
