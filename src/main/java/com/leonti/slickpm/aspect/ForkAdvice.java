package com.leonti.slickpm.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForkAdvice {   
	Logger log = LoggerFactory.getLogger(ForkAdvice.class); 
    
    public void fork(final ProceedingJoinPoint pjp) {  
        new Thread(new Runnable() {  
            public void run() {  
                log.info("Forking method execution: " + pjp);  
                try {  
                    pjp.proceed();  
                } catch (Throwable t) {  
                    // All we can do is log the error.  
                    log.error(t.getMessage());
                }  
            }  
        }).start();  
    }  
} 
