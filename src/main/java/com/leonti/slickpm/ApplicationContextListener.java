package com.leonti.slickpm;

import javax.annotation.Resource;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.leonti.slickpm.service.DemoService;

public class ApplicationContextListener implements ApplicationListener<ApplicationEvent> {

	@Resource(name="DemoService")
	DemoService demoService;		
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof ContextRefreshedEvent) {
        	demoService.populateDb();
        }		
	}	
}
