package com.frankmoley.lil.fid.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CounterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterAspect.class);
    Map<String, Integer> counters = new HashMap<>();

    @Pointcut("@annotation(CounterCall)")
    public void executeCounter() {
        //nothing pointcut
    }

    @Before(value = "executeCounter()")
    public void counter(JoinPoint joinPoint) {
        String nameMethod = joinPoint.getSignature().getName();
        StringBuilder message = new StringBuilder();
        addCounter(nameMethod);
        message.append("Method from call aspect counter: ").append(nameMethod);
        message.append(" count call method: ").append(counters.get(nameMethod));
        LOGGER.info(message.toString());

    }

    private void addCounter(String nameMethod) {
        Integer count = 1;
        if (counters.containsKey(nameMethod)) {
            count += counters.get(nameMethod);
        }
        counters.put(nameMethod, count);
    }
}
