# 并行与并发

并行：多个CPU同时执行多个任务。比如：多个人同时做不同的事。
并发：一个CPU(采用时间片)同时执行多个任务。比如：秒杀、多个人做同一件事。

# 创建线程有哪些方式

- 继承thread类,重写run方法,调用start。
- 实现Runnable接口,重写run方法,调用start。
- 实现Callable接口,传递泛型,重写call方法。
- 使用线程池。
- run()方法由JVM调用,什么时候调用由CPU调度决定。
- 多个线成只能调用一次start否则抛出异常java.lang.IllegalThreadStateException。

# 为什么要使用线程池

- 提前创建多个线程,放入线程池中,使用时直接获取,使用完放回池中。可以避免频繁创建销毁、实现重复利用。
  - 减少了创建新线程的时间（提高响应速度）。
  - 重复利用线程池中线程，不需要每次都创建（降低资源消耗）。
  - 便于线程管理比如:
    - 核心池的大小：corePoolSize
    - 最大线程数：maximumPoolSize
    - 线程没有任务时最多保持多长时间后会终止：keepAliveTime
- 常见工具类有:
  - Executors.newCachedThreadPool():创建一个可根据需要创建新线程的线程池
  - Executors.newFixedThreadPool(n):创建一个可重用固定线程数的线程池
  - Executors.newSingleThreadExecutor():创建一个只有一个线程的线程池
  - Executors.newScheduledThreadPool(n):创建一个线程池,可延迟执行或者定期执行。

# 实现Runable,Callable与继承Thread比较

> - 1.java中存在的单继承多实现机制
> - 2.接口代码可以被多个线程共享，代码和线程独立。
> - 3.线程池只能放Runable或Callable不能直接放入继承Thread的类。
> - 4.在java中，每次程序运行至少启动2个线程。一个是main线程，一个是垃圾收集线程。

# Runnable和Callable接口比较

```java
/**
 相同点：
 	都是接口,都可以完成多线程,都得调用Thread.start()启动线程；
 不同点：
 	1.Callable有返回值；Runnable没有返回值；
 	2.Callable可以抛异常[call()]；Runnable不可以抛异常[run()]；
 	3.Callable可以取消执行(Future.cancel),Runnable不能
 注意点：
 	Callable支持返回值，需要调用FutureTask.get()实现，此方法会阻塞主线程直到获取‘将来’结果；当不调用此方法时，主线程不会阻塞！
 */
```

# 线程安全问题

> * 1.同步代码块
>   *      锁对象,当前对象字节码,成员变量都可以
> * 2.同步方法
>   *      非static方法,同步锁是this.static方法,使用当前方法所在类的字节码对象(类名.class)
> * 3.Lock锁功能强大更体现面向对象 加锁与释放锁方法化

### 问题演示

```java
public class newTicket implements Runnable {
    private int ticktNum = 10;
    @Override
    public void run() {
        while(true) {
                if (ticktNum > 0) {
                    //1.模拟出票时间
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //2.打印进程号和票号，票数减1
                    String name = Thread.currentThread().getName();
                    System.out.println("线程" + name + "售票：" + ticktNum--);
                }
            }
    }
}
public class newTicketDemo {
    public static void main(String[] args){
        newTicket ticket = new newTicket();
        Thread thread1 = new Thread(ticket, "窗口1");
        Thread thread2 = new Thread(ticket, "窗口2");
        Thread thread3 = new Thread(ticket, "窗口3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
/**
 * 线程窗口1售票：10
 * 线程窗口3售票：9
 * 线程窗口2售票：8
 * 线程窗口1售票：7
 * 线程窗口3售票：6  问题吖
 * 线程窗口2售票：6  问题吖
 * 线程窗口3售票：5  问题吖
 * 线程窗口2售票：4
 * 线程窗口1售票：5  问题吖
 * 线程窗口2售票：2
 * 线程窗口1售票：1
 * 线程窗口3售票：3
 */
```

### 解决方式-线程同步

同步代码:块最多允许一个线程拥有同步锁,谁拿到锁就进入代码块,其他的线程只能在外等着

```java
public class newTicket implements Runnable {

    private int ticktNum = 10;
    Object obj = new Object(); //锁对象 锁的钥匙 索谁还不行 当前对象字节码 锁成员变量
    
    @Override
    public void run() {
        synchronized (obj) {
            while(true) {
                if (ticktNum > 0) {
                    //1.模拟出票时间
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //2.打印进程号和票号，票数减1
                    String name = Thread.currentThread().getName();
                    System.out.println("线程" + name + "售票：" + ticktNum--);
                }
            }
        }
    }
}
```

同步方法:synchronized修饰的方法,保证A线程执行该方法的时候,其他线程只能在方法外等着

对于非static方法,同步锁就是this.对于static方法,我们使用当前方法所在类的字节码对象(类名.class)		 

```java
public class newTicket implements Runnable {

    private int ticktNum = 10;
    @Override
    public void run() {
        while (true) {
            sellTicket();
        }
    }
    /**
     * 方法标注
     * 如果是静态方法,锁对象相当于
     * private static synchronized(newTicket.class) void sellTicket()
     * 如果不是静态方法，锁对象相当于
     * private  synchronized(new newTicket()) void sellTicket()
     */
    private synchronized void sellTicket() {
        if (ticktNum > 0) {
            //1.模拟出票时间
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //2.打印进程号和票号，票数减1
            String name = Thread.currentThread().getName();
            System.out.println("线程" + name + "售票：" + ticktNum--);
        }
    }
}
```

同步锁lock:功能强大更体现面向对象 加锁与释放锁方法化

```java
//public void lock():加同步锁
//public void unlock():释放同步锁
public class newTicket implements Runnable {
    private int ticktNum = 10;
    /**
     * 3.同步锁
     * 定义锁对象：构造函数参数为线程是否公平获取锁true-公平；
     * false-不公平，即由某个线程独占，默认是false
     */
    Lock lock = new ReentrantLock(true);
    public void run() {
        while(true){
            lock.lock(); //加锁
            try{
                if(ticktNum > 0){
                    //1.模拟出票时间
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //2.打印进程号和票号，票数减1
                    String name = Thread.currentThread().getName();
                    System.out.println("线程"+name+"售票："+ticktNum--);
                }
            } finally {
                lock.unlock(); //放锁
            }
        }
    }
}
```

### Synchronized和Lock区别

*   synchronized不能判断锁的状态,Lock可以判断是否获取到锁；
*   synchronized会在执行完毕或者抛出异常的时候自动释放锁，
    *   Lock需要调用unlock()释放锁,否则容易造成死锁；
*   用synchronized修饰的方法和代码块。
    *   线程A获得锁,线程b等待。
    *   线程A阻塞,线程B一直等待下去。
    *   而Lock锁就不一定会等待下去，如果尝试获取不到锁，线程可以不用一直等待就结束了。
*   大量同步用Lock,少量用synchronized

# 线程生命周期

> - 新建: 创建(Thread t = new MyThread())了一个线程 
> - 就绪:调用了start()方法 
>   - 线程已经做好了准备，随时等待CPU调度执行，并不是说执行了t.start()此线程立即就会执行
> - 运行:运行run()方法
>   - 就绪是进入到运行状态的唯一入口,线程要想进入运行状态执行,首先必须处于就绪状态中
> - 阻塞:睡眠(sleep),等待通知（notify）
>   - 阻塞又分三种
>     - a.等待阻塞:线程执行wait()方法,使本线程进入到等待阻塞状态。
>      - b.同步阻塞:获取synchronized同步锁失败(因为锁被其它线程所占用),它会进入同步阻塞状态。
>     - c.其他阻塞:通过调用线程的sleep()或join()或发出了I/O请求时,线程会进入到阻塞状态。
>       - 当sleep()状态超时、
>       - join()等待线程终止或者超时、
>        - 或者I/O处理完毕时，
>       - 线程重新转入就绪状态。
>  - 死亡:线程执行完了或者因异常退出了run()方法，该线程结束生命周期

# 什么是死锁

> 两个进程都在等待对方执行完毕才能继续往下执行的时候就发生了死锁。
>
> 结果就是两个进程都陷入了无限的等待中

# 如何避免线程死锁?

> 预防死锁是设法破坏产生死锁的四个必要条件之一
>
> - 破坏互斥条件,
> - 破坏占有并等待条件,
> - 破坏不可抢占条件,
> - 破坏循环等待条件

# 线程通讯

> - 多个线程并发执行时,默认情况CPU是随机切换线程的,有时我们希望CPU按我们的规律执行线程,此时就需要线程通讯。
> - Object的wait(进入等待状态),notify(唤醒),notifyAll(唤醒所有)
>   - wait与notify必须是同一个锁对象.
>   - 锁对象可以是任意对象,因为wait,notify属于Object类的方法,所有类都继承Object类。
>   - wait与notify必须要在同步代码块或者是同步函数中使用。因为：必须要通过锁对象调用这2个方法。
>   - wait只能通过Nodify,notifyAll方法进行唤醒
> - Condition的await、signal、signalAll
>   - await()必须和Lock(互斥锁/共享锁)配合使用
>   - await()必须通过signal(),signalAll()方法进行唤醒
> - 并发工具类
>   - CountDownLatch：线程A等待其他线程执行完在执行
>   - CyclicBarrier：一组线程等待至某个状态之后再全部同时执行
>   - Semaphore：用于控制对某组资源的访问权限

案例:生产者与消费者(生产包子吃包子),打印基偶数

```java
/**
 * 线程通讯
 * 1.Object的wait(进入等待状态),notify(唤醒),notifyAll(唤醒所有)
 *  细节:
 *      1.wait与notify必须是同一个锁对象.
 *      2.锁对象可以是任意对象,因为wait,notify属于Object类的方法,所有类都继承Object类。
 *      3.wait与notify必须要在同步代码块或者是同步函数中使用。因为：必须要通过锁对象调用这2个方法。
 *      4.wait只能通过Nodify,notifyAll方法进行唤醒
 * 2.Condition的await、signal、signalAll
 *  细节:
 *      1.await()必须和Lock(互斥锁/共享锁)配合使用
 *      2.await()必须通过signal(),signalAll()方法进行唤醒
 * 3.并发工具类
 * 3.1.CountDownLatch：用于某个线程A等待若干个其他线程执行完之后，它才执行
 * 3.2.CyclicBarrier：一组线程等待至某个状态之后再全部同时执行
 * 3.3.Semaphore：用于控制对某组资源的访问权限
 */
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;
import java.util.Date;
//Object的wait,notify,notifyAll
public class ObjectWaitNotifyRunnable {
    private Object obj = new Object();
    private Integer i = 0;

    public void odd() {
        while (i < 10) {
            synchronized (obj) {
                if (i % 2 == 1) {
                    System.out.println("奇数：" + i);
                    i++;
                    obj.notify();
                } else {
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void even() {
        while (i < 10) {
            synchronized (obj) {
                if (i % 2 == 0) {
                    System.out.println("偶数：" + i);
                    i++;
                    obj.notify();
                    obj.notifyAll();
                } else {
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        final ObjectWaitNotifyRunnable runnable = new ObjectWaitNotifyRunnable();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                runnable.odd();
            }
        }, "偶数线程");
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                runnable.even();
            }
        }, "奇数线程");

        t1.start();
        t2.start();
    }
}

//==================================================================================
//Condition的await、signal、signalAll
class ConditionWaitNotifyRunnable {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Integer i = 0;

    public void odd() {
        while (i < 10) {
            lock.lock();
            try {
                if (i % 2 == 1) {
                    System.out.println("奇数：" + i);
                    i++;
                    condition.signal();
                } else {
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public void even() {
        while (i < 10) {
            lock.lock();
            try {
                if (i % 2 == 0) {
                    System.out.println("偶数：" + i);
                    i++;
                    condition.signal();
                } else {
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) {
        ConditionWaitNotifyRunnable runnable = new ConditionWaitNotifyRunnable();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                runnable.odd();
            }
        }, "偶数线程");
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                runnable.even();
            }
        }, "奇数线程");

        t1.start();
        t2.start();
    }
}

//==================================================================================
//CountDownLatch

/**
 * 1.CountDownLatch是在java1.5被引入的，存在于java.util.concurrent包下。
 * 2.CountDownLatch这个类能够使一个线程等待其他线程完成各自的工作后再执行。
 * 3.CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量
 * 4.每当一个线程完成了自己的任务后，计数器的值就会减1。当计数器值到达0时，
 * 它表示所有的线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。
 */
class CountDown {
    private Integer i = 0;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void odd() {
        while (i < 10) {
            if (i % 2 == 1) {
                System.out.println("奇数：" + i);
                i++;
                countDownLatch.countDown();
            } else {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void even() {
        while (i < 10) {
            if (i % 2 == 0) {
                System.out.println("偶数：" + i);
                i++;
                countDownLatch.countDown();
            } else {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        final CountDown countDown = new CountDown();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                countDown.odd();
            }
        }, "奇数");
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                countDown.even();
            }
        }, "偶数");

        t1.start();
        t2.start();
    }
}

//=======================================================================================================================
//CyclicBarrier方式

/**
 * CyclicBarrier是在java1.5被引入的，存在于java.util.concurrent包下。
 * CyclicBarrier实现让一组线程等待至某个状态之后再全部同时执行。
 * CyclicBarrier底层是三个线程同时启动
 */
class CyclicBarrierDemo {
    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + "：准备...");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "启动完毕：" + new Date().getTime());
            }
        }, "线程1").start();
        new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + "：准备...");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "启动完毕：" + new Date().getTime());
            }
        }, "线程2").start();
        new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + "：准备...");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "启动完毕：" + new Date().getTime());
            }
        }, "线程3").start();
    }
}

//==========================================================================================================================
//Semaphore方式

/**
 * Semaphore是在java1.5被引入的，存在于java.util.concurrent包下。
 * Semaphore用于控制对某组资源的访问权限。工人使用机器工作
 */
class SemaphoreDemo {

    static class Machine implements Runnable {
        private int num;
        private Semaphore semaphore;

        public Machine(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        public void run() {
            try {
                semaphore.acquire();//请求机器
                System.out.println("工人" + this.num + "请求机器，正在使用机器");
                Thread.sleep(1000);
                System.out.println("工人" + this.num + "使用完毕，已经释放机器");
                semaphore.release();//释放机器
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int worker = 8;//工人数
        Semaphore semaphore = new Semaphore(3);//机器数
        for (int i = 0; i < worker; i++) {
            new Thread(new Machine(i, semaphore)).start();
        }
    }
}
```

# sleep和wait区别

> - 同步 
>   - wait只能在同步代码块中使用,否则抛出异常
>   - 不需要在同步方法或同步代码块中调用
> - 作用对象
>   - wait方法定义在obj类中，作用与对象本身
>   - sleep方法定义在thread中，作用与当前线程
> - 释放锁资源
>   - wait释放,sleep不释放
> - 唤醒条件
>   - 调用notity或者notityall方法
>   - 超时或者调用interrupt方法体
> - 方法属性
>   - wait是实例方法
>   - sleep是静态方法

# wait和notify区别

一个等待一个唤醒嘛

# 并发编程三要素,多线程特性？

```java
/**
 * 多线程编程要保证满足三个特性：
 * 1）原子性-> 原子性指的是一个或者多个操作，
 *            要么全部执行并且在执行的过程中不被其他操作打断，
 *            要么就全部都不执行。
 * 2）可见性-> 可见性指多个线程操作一个共享变量时，
 *            其中一个线程对变量进行修改后，
 *            其他线程可以立即看到修改的结果。
 * 3）有序性-> 有序性，即程序的执行顺
 *            序按照代码的先后顺序来执行。
 */
```

# 线成控制类-ThreadLocal

>  * 描述:
>    * 当数据是以线程为作用域并且不同线程具有不同的数据副本的时候,可以采用ThreadLocal.
>    * 比如数据库连接Connection,web开发中参数传递,每个请求处理线程都需要，但又不相互影响。
>  * 原理
>    * 1.在ThreadLocal类中有一个ThreadLocalMap，
>    * 2.每一个Thread都有一个ThreadLocalMap类型的变量threadLocals
>    * 3.threadLocals内部有一个Entry，Entry的key是ThreadLocal对象实例，value就是共享变量副本
>    * 4.ThreadLocal的get方法就是根据ThreadLocal对象实例获取共享变量副本
>    * 5.ThreadLocal的set方法就是根据ThreadLocal对象实例保存共享变量副本

# 原子类-原子类解决非原子性操作问题

```java
/**
 * 一.Java的java.util.concurrent.atomic包里面提供了很多可以进行原子操作的类，分为以下四类：
 * 1.基本类型：AtomicInteger、AtomicBoolean、AtomicLong
 * 2.数组：AtomicIntegerArray、AtomicLongArray
 * 3.引用：AtomicReference、AtomicStampedReference等
 * 4.属性：AtomicIntegerFieldUpdater、AtomicLongFieldUpdater
 * 提供这些原子类的目的就是为了解决基本类型操作的非原子性导致在多线程并发情况下引发的问题。
 */
```

# CAS相关

> - 什么是CAS
>   - CAS意思就是比较交换,乐观锁(如给记录加version来获取数据,性能较悲观锁有很大的提高。)
> - 原理
>   - AtomicInteger、AtomicBoolean 先对应方法
>     - (n++)  n.getAndIncrement()
>     - (++n)  n.incrementAndGet()
>     - (--n)   n.decrementAndGet() 
>     - (n--)   n.getAndDecrement(); 
>   - 方法getAndAddInt,getAndAddLong中代码大概意思如下
>     - 判断当前值和预期值是否相同。
>     - 相同认为没有线程更改过该值
>     - 不相同说明有线程更改过该值
> - CAS的问题
>   - CAS容易造成ABA问题
>     - 当前内存的值一开始是A，被另外一个线程先改为B然后再改为A，
>     - 那么当前线程访问的时候发现是A，cas则认为它没有被其他线程访问过。
>     - 在某些场景下这样是存在错误风险的（狸猫换太子）
>   - CAS造成CPU利用率增加
>     - CAS里面是一个循环判断的过程，如果线程一直没有获取到状态，cpu资源会一直被占用
>   - 不能保证代码块的原子性
>     - CAS机制所保证的只是一个变量的原子性操作，而不能保证整个代码块的原子性。
>     - 比如需要保证3个变量共同进行原子性的更新，就不得不使用synchronized了
> - CAS的ABA问题及解决
>   - AtomicStampedReference

# volatile关键字的作用

> 1.对于可见性，Java提供了volatile关键字来保证可见性。
>
> 2.当一个共享变量被volatile修饰时，它会保证修改的值会立即被更新到主存，
>
> 当有其他线程需要读取时，它会去内存中读取新值。
>
> 3.volatile的一个重要作用就是和CAS结合，保证了原子性比如AtomicInteger。
>
> 4.volatile只能保证可见性，不能保证原子性

# 实现可见性的方法有哪些？

> synchronized或者Lock：保证同一个时刻只有一个线程获取锁执行代码，锁释放之前把最新的值刷新到主内存，实现可见性。

# 线程B怎么知道线程A修改了变量

> volatile修饰变量
> synchronized修饰修改变量的方法
> object的wait/notify
>
> Condition的await、signal
>
> while轮询

# synchronized、volatile、CAS比较

> synchronized是悲观锁，属于抢占式，会引起其他线程阻塞。
> volatile提供多线程共享变量可见性和禁止指令重排序优化。
> CAS是基于冲突检测的乐观锁（非阻塞）

# 同步方法和同步块，哪个是更好的选择?

> 同步块，这意味着同步块之外的代码是异步执行的，这比同步整个方法更提升代码的效率。请知道一条原则：同步的范围越小越好。