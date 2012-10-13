package com.leonti.slickpm.aspect;

import java.util.logging.Logger;
import org.aspectj.lang.ProceedingJoinPoint;  

public class ForkAdvice {   
    protected static Logger logger = Logger.getLogger("Fork advice"); 
    
    public void fork(final ProceedingJoinPoint pjp) {  
        new Thread(new Runnable() {  
            public void run() {  
                logger.info("Forking method execution: " + pjp);  
                try {  
                    pjp.proceed();  
                } catch (Throwable t) {  
                    // All we can do is log the error.  
                    logger.severe(t.getMessage());
                }  
            }  
        }).start();  
    }  
} 
