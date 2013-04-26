package com.ybase.deamon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created By: YBASE
 * Created Date: 2013-4-26 下午2:56:01
 * Author: Tom Yang
 */

/**
 * @author Tom Yang
 * @version 1.0
 */
public class DSThread extends Thread implements IDeamonThread {
	
	private static final Log logger = LogFactory.getLog(DSThread.class);
	
	private boolean running = true;
	
	private boolean stop = false;
	
	private IDeamon deamon;
	
	private Integer ds;
	
	public DSThread(IDeamon deamon,Integer ds) {
		this.deamon = deamon;
		this.ds = ds;
	}
	
	public void run() {
		while(!isStop()) {
			try {
				deamon.doTask();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			try {
				Thread.sleep(ds);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		setRunning(false);
		logger.info("DSThread stop...");
	}

	public void stopThread() {
		deamon.stop();
		setStop(true);
	}

	public IDeamon getDeamon() {
		return deamon;
	}

	public synchronized boolean isRunning() {
		return running;
	}

	public synchronized boolean isStop() {
		return stop;
	}

	public synchronized void setStop(boolean stop) {
		this.stop = stop;
	}

	private synchronized void setRunning(boolean running) {
		this.running = running;
	}

}
