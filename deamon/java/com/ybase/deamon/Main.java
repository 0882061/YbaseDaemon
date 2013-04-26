package com.ybase.deamon;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

/**
 * Created By: YBASE
 * Created Date: 2013-4-26 下午2:18:47
 * Author: Tom Yang
 */

/**
 * @author Tom Yang
 * @version 1.0
 */
public class Main implements WrapperListener {

	private static final Log logger = LogFactory.getLog(Main.class);

	public void controlEvent(int event) {
		// TODO Auto-generated method stub
		if (WrapperManager.isControlledByNativeWrapper()) {
			// The Wrapper will take care of the event

		} else {
			if (event == WrapperManager.WRAPPER_CTRL_C_EVENT || event == WrapperManager.WRAPPER_CTRL_SHUTDOWN_EVENT || event == WrapperManager.WRAPPER_CTRL_CLOSE_EVENT) {
				WrapperManager.stop(0);
			}
		}
	}

	public Integer start(String[] args) {
		if(args.length<2) {
			throw new IllegalArgumentException("");
		}
		
		//TODO
		return null;
	}

	public int stop(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
