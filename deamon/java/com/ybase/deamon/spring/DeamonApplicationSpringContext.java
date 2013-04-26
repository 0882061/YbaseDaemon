/**
 * Created By: YBASE
 * Created Date: 2013-4-26 下午4:17:02
 * Author: Tom Yang
 */
package com.ybase.deamon.spring;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ybase.deamon.DeamonAppliction;
import com.ybase.deamon.IDeamon;
import com.ybase.deamon.IDeamonThread;

/**
 * @author Tom Yang
 * @version 1.0
 */
public class DeamonApplicationSpringContext extends DeamonAppliction{
	
	private static final Log logger = LogFactory.getLog(DeamonApplicationSpringContext.class);
	
	protected ApplicationContext context;
	
	protected void init() {
		String springConfigFiles = "spring/applicationContext*.xml";
		InputStream is = DeamonApplicationSpringContext.class.getResourceAsStream("/daemon.properties");
		if(is!=null) {
			try {
				Properties props = new Properties();
				props.load(is);
				is.close();
				springConfigFiles = props.getProperty("spring.config.files", springConfigFiles);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		context = new ClassPathXmlApplicationContext(springConfigFiles.split(","));
	}
	
	public void start(String args) {
		logger.info("start all appliction daemon ...");
		init();
		
		try {
			parseExecFile(new File(args));
			for(int i=0;i<deamons.size();i++) {
				IDeamonThread threadDeamon = (IDeamonThread)deamons.get(i);
				IDeamon deamon = threadDeamon.getDeamon();
				if(deamon instanceof IDeamonSpringContext) {
					((IDeamonSpringContext)deamon).init(context);
				}else {
					deamon.init();
				}
				threadDeamon.start();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return ;
		}
		logger.info("finish all appliction daemon ...");
	}
	
	protected IDeamon instanceDeamon(String className) {
		try {
			Class classz = Class.forName(className);
			if(IDeamon.class.isAssignableFrom(classz)||IDeamonSpringContext.class.isAssignableFrom(classz)) {
				return (IDeamon)classz.newInstance();
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
	
	public static void main(String[] args) throws Exception {
		DeamonApplicationSpringContext app = new DeamonApplicationSpringContext();
		app.start("deamon/conf/exec-example.conf");

		Thread.sleep(8000);
		app.stop();
	}
}
