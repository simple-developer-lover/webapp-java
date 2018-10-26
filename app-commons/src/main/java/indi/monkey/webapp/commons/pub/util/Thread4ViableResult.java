package indi.monkey.webapp.commons.pub.util;

import java.util.ArrayList;
import java.util.List;
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
				if (p != null) {
					if (p.test(t)) {
						break;
					}
				} else {
					if (t != null) {
						break;
					}
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
				Runnable r = QUEUE.poll();
				if (r != null) {
					r.run();
				}
			}
		}

	}

	public static void test() {
		int i = 0;
		System.err.println(">>>>>>>>>>>>>>>>>>> start");
		List<Thread> ts = new ArrayList<>(10);
		while (i < 10) {
			Thread t = new Thread(() -> {
				for (int a = 0; a < 100; a++) {
					try {
						Thread.sleep(100);
						System.err.println("sleep ...");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					addTask(new Thread(() -> {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(">>>>>>>>> execute ......111111");
					}));
				}
			});
			t.start();
			ts.add(t);
			i++;
		}
		for (Thread t : ts) {
			try {
				t.join(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.err.println(">>>>>>>>>>>>>>>>>>> end");
	}

	public static void main(String[] args) throws InterruptedException {
		test();
	}

}
