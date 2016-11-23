package com.luckymiao.common.utils;

import org.apache.log4j.Logger;

public class LogUtils extends Logger {
	
	public static final String LOG_CONFIG_FILE = "log4j_config.xml";

	public LogUtils(String name) {
		super(name);
	}

	public static Logger getLogger(String name) {
		return Logger.getLogger(name);
	}
}
