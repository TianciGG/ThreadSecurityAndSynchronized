package chauncy.threadtrain;

class ThreadTrain implements Runnable {
	// 火车票总数
	private int count = 100;
	//两个线程一定要用同一把锁
	private Object obj=new Object();

	@Override
	public void run() {
		while (count > 0) {
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
			}
			synchronized(obj){
				// 100总数减去现有数量count加1为当前出售的第几张票
				System.out.println(Thread.currentThread().getName() + "出售第" + (100 - count + 1) + "张票");
				count--;
			}
		}
	}
}

/**
 * 什么是线程不安全 
 * 当多个线程同时操作同一个共享的全局变量，可能会受到其他线程的干扰。会发生数据冲突的问题。
 * 线程不安全问题怎么解决？
 * 使用synchronize、jdk1.5的并发包lock
 * 使用synchronized关键字包裹起来的代码
 * 每次只能让当前一个线程进行执行
 * @classDesc: 功能描述(模拟线程不安全问题)
 * @author: ChauncyWang
 * @createTime: 2019年3月6日 下午5:41:06
 * @version: 1.0
 */
public class ThreadDemo1 {
	public static void main(String[] args) {
		//线程类一定要用一个实例，因为要重现变量共享的问题
		ThreadTrain threadTrain = new ThreadTrain();
		// 1.创建两个线程
		Thread thread1 = new Thread(threadTrain);
		Thread thread2 = new Thread(threadTrain);
		thread1.start();
		thread2.start();
	}
}
