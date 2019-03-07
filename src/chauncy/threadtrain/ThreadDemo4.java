package chauncy.threadtrain;

class ThreadTrain4 implements Runnable {
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
				//入参为obj情况下线程不安全，为this情况线程安全，证明同步函数使用的是this锁。
				synchronized(ThreadDemo4.class){
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
		}else{
			while (count > 0) {
				show();
			}
		}
	}

	/**
	 * static 如果修饰的方法，直接通过类名.方法名进行调用   当class文件也是字节码文件被加载时，才会被初始化。 static存放在方法区 永久区
	 * this关键字表示当前对象的锁，static使用类.class的锁
	 * @methodDesc: 功能描述()  
	 * @author: ChauncyWang
	 * @param:    
	 * @createTime: 2019年3月7日 下午4:03:09   
	 * @returnType: void
	 */
	public static synchronized void show() {
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
 * @classDesc: 功能描述(静态同步函数)
 * @author: ChauncyWang
 * @createTime: 2019年3月6日 下午5:41:06
 * @version: 1.0
 */
public class ThreadDemo4 {
	public static void main(String[] args) throws InterruptedException {
		//线程类一定要用一个实例，因为要重现变量共享的问题
		ThreadTrain4 threadTrain4 = new ThreadTrain4();
		// 1.创建两个线程
		Thread thread1 = new Thread(threadTrain4);
		Thread thread2 = new Thread(threadTrain4);
		thread1.start();
		Thread.sleep(10);
		threadTrain4.flag=false;
		thread2.start();
	}
}
