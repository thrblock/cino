package com.thrblock.cino.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CINODemo {
	private static final Logger LOG = LogManager.getLogger(CINODemo.class);
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		context.registerShutdownHook();
		LOG.info(context);
	}
}
