/**
 * Created By: YBASE
 * Created Date: 2013-4-26 下午2:50:06
 * Author: Tom Yang
 */
package com.ybase.deamon;

/**
 * @author Tom Yang
 * @version 1.0
 */
public interface IDeamonThread {
	
	public void start();
	
	public void stopThread();
	
	public IDeamon getDeamon();
	
	public boolean isRunning();
}
