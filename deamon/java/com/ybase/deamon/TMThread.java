package com.ybase.deamon;

import java.util.Calendar;

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
public class TMThread extends Thread implements IDeamonThread {

	private static final Log logger = LogFactory.getLog(TMThread.class);

	private boolean running = true;

	private boolean stop = false;

	private IDeamon deamon;

	private int hour;

	private int minute;

	public static Log getLogger() {
		return logger;
	}

	public void setDeamon(IDeamon deamon) {
		this.deamon = deamon;
	}

	public TMThread(IDeamon deamon, String timer) {
		this.deamon = deamon;
		if (timer == null || timer.indexOf(":") < 0) {
			throw new IllegalArgumentException("illegal time format [HH:mm].");
		}

		String[] timers = timer.split(":");
		try {
			this.hour = Integer.parseInt(timers[0]);
		} catch (Exception e) {
			throw new IllegalArgumentException("illegal time format [HH:mm].");
		}
		try {
			this.minute = Integer.parseInt(timers[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("illegal time format [HH:mm].");
		}
	}

	public void run() {
		while (!isStop()) {
			int interval = 1000;
			if (equalsToTimer()) {
				try {
					deamon.doTask();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				interval = 60000;
				logger.info(getClassName()+"end task...");
			}
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(),e);
			}
		}
		setRunning(false);
		logger.info("TMThread stop...");
	}

	private String getClassName() {
		return deamon.getClass().getName();
	}

	private boolean equalsToTimer() {
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		return (this.hour == hour && this.minute == minute);
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

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

}
