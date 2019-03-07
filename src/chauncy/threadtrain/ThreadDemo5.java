package chauncy.threadtrain;

class ThreadTrain5 implements Runnable {
	// 火车票总数
	private static int count = 100;
	//两个线程一定要用同一把锁
	private Object obj = new Object();
	public boolean flag=true;

	@Override
	public void run() {
		//线程1 flag为true 线程2 flag为false
		if(flag){
			while (count > 0) {
				//锁一般是在代码执行完毕之后自动释放，让其它线程拿到锁执行
				//如果flag为true 先拿到obj这把锁，再拿到this锁，才能执行代码
				//如果flag为false 先拿到this这把锁，再拿到obj锁，才能执行代码
				synchronized(obj){
					show();
				}
			}
		}else{
			while (count > 0) {
				show();
			}
		}
	}


	public synchronized void show() {
		synchronized(obj){
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
}

/**
 * @classDesc: 功能描述(死锁实现)
 * @author: ChauncyWang
 * @createTime: 2019年3月6日 下午5:41:06
 * @version: 1.0
 */
public class ThreadDemo5 {
	public static void main(String[] args) throws InterruptedException {
		//线程类一定要用一个实例，因为要重现变量共享的问题
		ThreadTrain5 threadTrain5 = new ThreadTrain5();
		// 1.创建两个线程
		Thread thread1 = new Thread(threadTrain5);
		Thread thread2 = new Thread(threadTrain5);
		thread1.start();
		Thread.sleep(40);
		threadTrain5.flag=false;
		thread2.start();
	}
}
