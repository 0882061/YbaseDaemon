/**
 * Created By: YBASE
 * Created Date: 2013-4-26 下午2:33:58
 * Author: Tom Yang
 */
package com.ybase.deamon;

/**
 * @author Tom Yang
 * @version 1.0
 */
public interface IDeamon {
	
	public void init();
	
	public void doTask();
	
	public void stop();
}
