package chauncy.threadtrain;

class ThreadTrain2 implements Runnable {
	// 火车票总数
	private int count = 100;
	//两个线程一定要用同一把锁
	private Object obj = new Object();

	@Override
	public void run() {
		while (count > 0) {
			show();
		}
	}

	public synchronized void show() {
		if (count > 0) {
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
			}
			// 100总数减去现有数量count加1为当前出售的第几张票
			System.out.println(Thread.currentThread().getName() + "出售第" + (100 - count + 1) + "张票");
			count--;
		}
	}
}

/**
 * @classDesc: 功能描述(使用同步函数(同步方法))
 * @author: ChauncyWang
 * @createTime: 2019年3月6日 下午5:41:06
 * @version: 1.0
 */
public class ThreadDemo2 {
	public static void main(String[] args) {
		//线程类一定要用一个实例，因为要重现变量共享的问题
		ThreadTrain2 threadTrain2 = new ThreadTrain2();
		// 1.创建两个线程
		Thread thread1 = new Thread(threadTrain2);
		Thread thread2 = new Thread(threadTrain2);
		thread1.start();
		thread2.start();
	}
}
