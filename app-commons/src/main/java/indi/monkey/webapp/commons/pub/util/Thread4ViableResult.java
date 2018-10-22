package indi.monkey.webapp.commons.pub.util;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Thread4ViableResult {

	public static <T> T execute(Set<Callable<T>> calls, int serviceSize, Predicate<T> p) {
		ExecutorService es = Executors.newFixedThreadPool(serviceSize);
		final BlockingQueue<Future<T>> queue = new LinkedBlockingQueue<>();
		final CompletionService<T> service = new ExecutorCompletionService<>(es, queue);
		T t = null;
		calls.forEach(c -> {
			service.submit(c);
		});
		try {
			for (int i = 0; i < calls.size(); i++) {
				t = service.take().get();
				if (p.test(t)) {
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
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
				QUEUE.put(runnable);
				synchronized (LOCK) {
					LOCK.notifyAll();
				}
			} catch (InterruptedException e) {
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
			while (true) {
				if (QUEUE.isEmpty()) {
					try {
						synchronized (LOCK) {
							LOCK.wait();
						}
						log.info(">>>>> lock  id:{}", id);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(".................................................");
				Runnable r = QUEUE.poll();
				if (r != null) {
					r.run();
					log.info(">>>>>>>>>>> id:{} ... run,QUEUE size:{}", id, QUEUE.size());
				}
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		log.info("start ...");
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			addTask(new Thread(() -> System.out.println(">>>>>>>>> execute ......111111")));
		}
		Thread.sleep(4000);
		log.info("main thread has wait for {}ms ,start add the secend tasks...",(System.currentTimeMillis() - startTime));
		for (int i = 0; i < 10; i++) {
			addTask(new Thread(() -> System.out.println(">>>>>>>>> execute ......22222")));
		}
		Thread.sleep(5000);
		log.info("main thread has wait for {}ms ,start add the secend tasks...",(System.currentTimeMillis() - startTime));
		for (int i = 0; i < 10; i++) {
			addTask(new Thread(() -> System.out.println(">>>>>>>>> execute ......3333")));
		}

	}

}
