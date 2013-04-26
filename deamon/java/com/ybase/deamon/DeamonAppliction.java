/**
 * Created By: YBASE
 * Created Date: 2013-4-26 下午3:29:10
 * Author: Tom Yang
 */
package com.ybase.deamon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Tom Yang
 * @version 1.0
 */
public class DeamonAppliction implements IAppcationWrapper {

	private static final Log logger = LogFactory.getLog(DeamonAppliction.class);

	protected final List deamons = new ArrayList();

	public void start(final String args) {
		logger.info("start all appliction deamon...");
		try {
			parseExecFile(new File(args));

			for (int i = 0; i < deamons.size(); i++) {
				try {
					IDeamonThread deamonThread = (IDeamonThread) deamons.get(i);
					deamonThread.getDeamon().init();
					deamonThread.start();// 启动线程
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return;
		}
		logger.info("finish all appliction deamon...");
	}

	/**
	 * @param file
	 */
	protected void parseExecFile(File file) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line;
		while ((line = br.readLine()) != null) {
			line = getCommandLine(line);
			if (line == null || line.length() <= 0) {
				continue;
			}

			IDeamonThread deamon = buildDeamonThread(line);
			if (deamon != null) {
				deamons.add(deamon);
				logger.info("create deamon " + line);
			}
		}
		br.close();
	}

	/**
	 * @param line
	 * @return
	 */
	private IDeamonThread buildDeamonThread(String line) {
		String[] cmdinfo = line.split("=");
		if (cmdinfo.length <= 1) {
			return buildDSThreadDeamon(cmdinfo[0], 1000);
		}

		String[] deamonInfo = cmdinfo[1].split(",");
		if ("ds".equals(deamonInfo[0])) {
			Integer ds = 1;
			if (deamonInfo.length >= 2) {
				try {
					ds = new Integer(deamonInfo[1]);
				} catch (NumberFormatException e) {
					logger.error(e.getMessage(), e);
				}
			}
			return buildDSThreadDeamon(cmdinfo[0], ds);
		} else if ("tm".equals(deamonInfo[0]) && deamonInfo.length >= 2) {
			return buildTimerThreadDeamon(cmdinfo[0], deamonInfo[1]);
		} else {
			logger.error("invalid daemon type:[" + deamonInfo[0] + "]");
		}
		return null;
	}

	/**
	 * @param className
	 * @param tm
	 * @return
	 */
	private IDeamonThread buildTimerThreadDeamon(String className, String tm) {
		IDeamon deamon = instanceDaemon(className);
		if (deamon != null) {
			try {
				return new TMThread(deamon, tm);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			logger.error("Class[" + className + "] is not instance of IDeamon");
		}
		return null;
	}

	/**
	 * @param className
	 * @param ds
	 * @return
	 */
	private IDeamonThread buildDSThreadDeamon(String className, int ds) {
		IDeamon deamon = instanceDaemon(className);
		if (deamon != null) {
			return new DSThread(deamon, ds);
		} else {
			logger.error("Class[" + className + "] is not an instance of IDeamon!");
		}
		return null;
	}

	/**
	 * @param className
	 * @return
	 */
	protected IDeamon instanceDaemon(String className) {
		try {
			Class classz = Class.forName(className);
			if (IDeamon.class.isAssignableFrom(classz)) {
				return (IDeamon) classz.newInstance();
			}
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @param line
	 * @return
	 */
	private String getCommandLine(String line) {
		int index = line.indexOf("#");
		if (index < 0) {
			index = line.length();
		}
		return line.substring(0, index).trim();
	}

	public void stop() {
		logger.info("stop all appliction daemon...");
		for (int i = 0; i < deamons.size(); i++) {
			stopDaemonThread((IDeamonThread) deamons.get(i));
		}
		logger.info("finish stop all appliction daemon...");
	}

	/**
	 * @param deamonThread
	 */
	private void stopDaemonThread(IDeamonThread deamonThread) {
		deamonThread.stopThread();

		int count = 0;
		while (deamonThread.isRunning() && count < 10) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			count++;
		}
		logger.info("stop thread, count:[" + count + "]");
	}

	public static void main(String[] args)throws Exception {
		DeamonAppliction deamonApp = new DeamonAppliction();
		deamonApp.start("deamon/conf/exec-example.conf");
		Thread.sleep(8000);
		deamonApp.stop();
	}

}
