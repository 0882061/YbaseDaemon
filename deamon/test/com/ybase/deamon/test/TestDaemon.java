/**
 * Created By: YBASE
 * Created Date: 2013-4-26 下午4:45:58
 * Author: Tom Yang
 */
package com.ybase.deamon.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ybase.deamon.base.BaseDeamon;

/**
 * @author Tom Yang
 * @version 1.0
 */
public class TestDaemon extends BaseDeamon {

	private static final Log logger = LogFactory.getLog(TestDaemon.class);
	
	public void init() {
		logger.info("TestDaemon init....");
	}
	
	public void doTask() {
		logger.info("TestDaemon task start....");
	}
	
	public void stop() {
		logger.info("TestDaemon task stop...");
	}
}
