package com.mwf.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Resubmit {
	
	
	 private static Lock lock = new ReentrantLock();
	    /*
	     * 使用完毕释放后其他线程才能获取锁
	     */
	    public String lockTest() throws InterruptedException {
	    	 System.out.println("线程"+Thread.currentThread().getName() + "提交方法"); 
	    	 System.out.println(lock.tryLock());
	    	//如果获取锁失败，则证明存在锁，则判断重复提交
	    	if(lock.tryLock()){
	    		lock.lock();//获取锁
		        try {
		            System.out.println("线程"+Thread.currentThread().getName() + "获取当前锁"); //打印当前锁的名称
		            Thread.sleep(2000);//为看出执行效果，是线程此处休眠2秒
		        } catch (Exception e) {
		            System.out.println("线程"+Thread.currentThread().getName() + "发生了异常释放锁");
		        }finally {
		            System.out.println("线程"+Thread.currentThread().getName() + "执行完毕释放锁");
		            lock.unlock(); //释放锁
		        }
		        System.out.println("线程"+Thread.currentThread().getName() + "执行成功"); //打印当前锁的名称
		        return "执行成功";
	    		
	    	}else{
	    		System.out.println("线程"+Thread.currentThread().getName() + "重复提交"); //打印当前锁的名称
	    		return "重复提交";
	        }
	    }
	     
	    public static void main(String[] args) {
	    	new Thread(new Thread1()).start();;
	    	new Thread(new Thread2()).start();
	    }
	    
	    

}
class Thread1 implements Runnable{
	Resubmit lockTest = new Resubmit();
	@Override
	public void run() {
		try {
			lockTest.lockTest();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
class Thread2 implements Runnable{
	Resubmit lockTest = new Resubmit();
	@Override
	public void run() {
			try {
				lockTest.lockTest();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}

